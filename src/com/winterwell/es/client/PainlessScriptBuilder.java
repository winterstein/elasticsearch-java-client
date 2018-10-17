package com.winterwell.es.client;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import com.winterwell.utils.StrUtils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.containers.Containers;
import com.winterwell.utils.io.FileUtils;
import com.winterwell.utils.time.Time;
import com.winterwell.web.data.XId;

/**
 * Make Painless scripts
 * @author daniel
 * @testedby {@link PainlessScriptBuilderTest}
 * 
 */
public class PainlessScriptBuilder {

	private String script;

	Map params = new ArrayMap();

	private Map<String, Object> jsonObject;

	/**
	 * parameters that should not be merged (use overwrite instead)
	 */
	private Set<String> hardSetParams = new HashSet();

	public static String basicScript = loadScript(PainlessScriptBuilder.class.getResourceAsStream(
			"update.painless.js"));
	

	/**
	 * parameters that should not be merged (use overwrite instead)
	 */
	public PainlessScriptBuilder setNoMergeParam(String p) {
		hardSetParams.add(p);
		return this;
	}
	
	public Map getParams() {
		return params.isEmpty()? null : params;
	}

	public String getLang() {
		return "painless";
	}
	
	/**
	 * use static methods to make??
	 */
	PainlessScriptBuilder() {
	}

	public String getScript() {
		if (script==null) buildScript();
		return script;
	}

	private void buildScript() {
		if (jsonObject!=null) {
			StringBuilder sb = new StringBuilder();
			String var = "e";
			fromJsonObject2(jsonObject, sb, var);
			script = sb.toString();
		}
	}

	/**
	 * Make an update script for this doc.
	 * @param doc
	 * @return this
	 */
	public static PainlessScriptBuilder fromJsonObject(Map<String, Object> doc) {
		// TODO maybe refactor to use Merger and Diff from Depot??
		// NB use Debug.explain(var) to get debug info out
		PainlessScriptBuilder psb = new PainlessScriptBuilder();
		psb.setJsonObject(doc);
		return psb;
	}
	
	public void setJsonObject(Map<String, Object> jsonObject) {
		assert script == null;
		this.jsonObject = jsonObject;
	}

	private void fromJsonObject2(Map<String, Object> doc, StringBuilder sb, String var) {		
		if (hardSetParams.isEmpty()) {
			fromJsonObject2_reusableScript(doc, sb);
			return;
		}
		
		sb.append("Map e=ctx._source;\n");
		for(Map.Entry me : doc.entrySet()) {
			final String k = (String) me.getKey();
			Object v = me.getValue();
			if (v==null) continue;
			
			// hard-set parameter? This allows for _not_ doing the collection merge below
			if (hardSetParams.contains(k)) {
				String pid = addParam(v);
				sb.append(var+"."+k+"=params."+pid+";\n");
				continue;
			}
			
			// collections?
			if (v instanceof Collection || v.getClass().isArray()) {
				String el = var+"."+k;
				List<Object> vlist = Containers.asList(v);
				// shove into params
				String pid = addParam(vlist);
				String rlist = "params."+pid; 
				// This fugly code does set-style uniqueness. If there is a nicer way please do say.
				// I assume naming the language "painless" is ES's joke on the rest of us.
				sb.append("if ("+el+"!=null) {for(int i=0; i<"+rlist+".size(); i++) {def x="+rlist+".get(i); if (!"+el+".contains(x)) "+el+".add(x);}} else {"+el+"="+rlist+";}\n");
				continue;
			} else if (v instanceof Map) {
				String el = var+"."+me.getKey();
				Map vmap = (Map) v;								
				// shove into params
				String pid = addParam(vmap);
				// TODO recurse??
				sb.append("if ("+el+"==null) "+el+"=params."+pid+"; else "+el+".putAll(params."+pid+");\n");
				continue;
			}
			
			String vs;
			if (v instanceof String) {
				vs = StrUtils.convertToJavaString((String) v);
			} else if (v instanceof Time) {
				vs = '"'+((Time)v).toISOString()+'"'; 
			} else if (v instanceof XId) {
				vs = StrUtils.convertToJavaString(v.toString());
			} else {
				// just number, boolean I think??
				vs = v.toString();
			}
			if (StrUtils.isWord(k)) {
				sb.append(var+"."+k+" = "+vs+";\n");
			} else {
				// handle e.g. "@class"
				sb.append(var+".put(\""+k+"\","+vs+");\n");
			}
		}	
	}

	private void fromJsonObject2_reusableScript(Map<String, Object> doc, StringBuilder sb) {
		doc = deepArrayToList(doc);
		String p = addParam(doc);
		assert p.equals("p0") : p;
		
		String s = basicScript;
		
		sb.append(s);
		// Recursion!
		// Painless function??
		// Use a while loop??		
//		List<Object[]> todo = new ArrayList();
//		while( ! todo.isEmpty()) {
//			Object[] obj_prop_val = todo.remove(todo.size()-1);
//			
//		}

		// This fugly code does set-style uniqueness. If there is a nicer way please do say.
		// I assume naming the language "painless" is ES's joke on the rest of us.
	}

	/**
	 * 
	 * @param r e.g. PainlessScriptBuilder.class.getResourceAsStream("update.painless.js");
	 * @return
	 */
	public static String loadScript(InputStream r) {
		assert r != null;
		String s = FileUtils.read(r);
//		assert ! s.contains("oldlc");
		// remove comments
		s = Pattern.compile("^\\s*//.*$", Pattern.MULTILINE).matcher(s).replaceAll(" ");
		// remove tabs
		s = StrUtils.compactWhitespace(s); // NOT universally valid, but fine for this script.
		return s;
	}

	/**
	 * Standardise on List not Object[] or String[] for saner Painless scripts
	 * @param doc
	 * @return
	 */
	Map<String, Object> deepArrayToList(Map doc) {
		Map doc2 = new ArrayMap();
		for(Object k : doc.keySet()) {
			Object v = doc.get(k);
			if (v==null) continue;
			if (v instanceof Map) {
				v = deepArrayToList((Map)v);
			} else if (v instanceof List) {
				v = deepArrayToList2((List)v);			
			} else if (v.getClass().isArray()) {
				v = deepArrayToList2(Containers.asList(v));
			}
			doc2.put(k, v);
		}
		return doc2;
	}

	private List deepArrayToList2(List list) {
		List vl2 = new ArrayList();
		for (Object vli : list) {
			if (vli==null) continue;
			if (vli instanceof Map) {
				vli = deepArrayToList((Map)vli);
			} else if (vli instanceof List) {
				vli = deepArrayToList2((List)vli);
			} else if (vli.getClass().isArray()) {
				vli = deepArrayToList2(Containers.asList(vli));
			}
			vl2.add(vli);
		}
		return vl2;
	}

	private String addParam(Object paramValue) {
		String pid = "p"+params.size();
		params.put(pid, paramValue);
		return pid;
	}

	@Override
	public String toString() {
		return "PainlessScriptBuilder [script=\n" + script + "\nparams=" + params + "]";
	}

	
}
