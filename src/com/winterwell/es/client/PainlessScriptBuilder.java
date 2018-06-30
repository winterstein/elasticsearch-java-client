package com.winterwell.es.client;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.lucene.index.MergeRateLimiter;

import com.winterwell.utils.StrUtils;
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.containers.Containers;
import com.winterwell.utils.time.Time;
import com.winterwell.web.data.XId;

/**
 * Make Painless scripts
 * @author daniel
 *
 */
public class PainlessScriptBuilder {

	private String script;

	Map params = new ArrayMap();
	
	public Map getParams() {
		return params.isEmpty()? null : params;
	}

	public String getLang() {
		return "painless";
	}

	public String getScript() {
		return script;
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
		StringBuilder sb = new StringBuilder("Map e=ctx._source;\n");
		String var = "e";
		fromJsonObject2(doc, psb, sb, var);
		psb.script = sb.toString();
		return psb;
	}

	private static void fromJsonObject2(Map<String, Object> doc, PainlessScriptBuilder psb, StringBuilder sb, String var) {
		for(Map.Entry me : doc.entrySet()) {
			Object v = me.getValue();
			if (v==null) continue;
			
			// collections?
			if (v instanceof Collection || v.getClass().isArray()) {
				String el = var+"."+me.getKey();
				List<Object> vlist = Containers.asList(v);
				// shove into params
				String pid = psb.addParam(vlist);								
				// ??uniqueness?? Left for the user -- get the last value				
				sb.append("if ("+el+"!=null) "+el+".addAll(params."+pid+"); else "+el+"=params."+pid+";\n");
				continue;
			} else if (v instanceof Map) {
				String el = var+"."+me.getKey();
				Map vmap = (Map) v;								
				// shove into params
				String pid = psb.addParam(vmap);
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
			String k = (String) me.getKey();
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
