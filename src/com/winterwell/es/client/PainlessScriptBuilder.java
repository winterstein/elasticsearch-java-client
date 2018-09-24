package com.winterwell.es.client;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.winterwell.utils.StrUtils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.containers.Containers;
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
			StringBuilder sb = new StringBuilder("Map e=ctx._source;\n");
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
