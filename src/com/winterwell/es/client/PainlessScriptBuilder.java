package com.winterwell.es.client;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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

	public static PainlessScriptBuilder fromJsonObject(Map<String, Object> doc) {
		// NB use Debug.explain(var) to get debug info out
		PainlessScriptBuilder psb = new PainlessScriptBuilder();
		StringBuilder sb = new StringBuilder("Map e=ctx._source;\n");
		String var = "e";
		for(Map.Entry me : doc.entrySet()) {
			Object v = me.getValue();
			if (v==null) continue;			
			String vs;
			if (v instanceof String) {
				vs = StrUtils.convertToJavaString((String) v);
			} else if (v instanceof Time) {
				vs = '"'+((Time)v).toISOString()+'"'; 
			} else if (v instanceof Collection || v.getClass().isArray()) {
				String el = var+"."+me.getKey();
				List<Object> vlist = Containers.asList(v);
				
//				// HACK special case props!!!
//				if ("props".equals(me.getKey())) {
//					sb.append("if ("+el+"==null) "+el+" = new ArrayList();\n");
//					for (Object _kv : vlist) {
//						Map kv = (Map) _kv;
//						String k = (String) kv.get("k");
//						String pid = psb.addParam(kv);
//						sb.append("if () el.add(params."+pid+")");
//						List x;						
//					}					
//					continue;
//				}
				
				String pid = psb.addParam(vlist);				
				
				// TODO uniqueness??
				// Left for the user -- get the last value
				
				sb.append("if ("+el+"!=null) "+el+".addAll(params."+pid+"); else "+el+"=params."+pid+";\n");
				continue;
			} else if (v instanceof XId) {
				vs = StrUtils.convertToJavaString(v.toString());
			} else {
				// just number, boolean I think??
				vs = v.toString();
			}
			sb.append(var+"."+me.getKey()+" = "+vs+";\n");
		}	
		psb.script = sb.toString();
		return psb;
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
