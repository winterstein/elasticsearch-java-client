package com.winterwell.es.client;

import java.util.Arrays;

import com.winterwell.utils.StrUtils;
import com.winterwell.utils.containers.ArrayMap;

public class ESHttpRequestWithBody<SubClass, ResponseSubClass extends IESResponse> extends ESHttpRequest<SubClass, ResponseSubClass>{


	public ESHttpRequestWithBody(ESHttpClient hClient) {
		super(hClient);
	}
	
	ArrayMap<String,Object> body 		= new ArrayMap();
	
	@Override
	public String getBodyJson() {
		if (body==null || body.isEmpty()) {
			return null;
		}
		// flatten the body into src
		// Note: we have to avoid wrapping the guts as strings. E.g. "{query: "{...}"}" would be a bug
		StringBuilder _src = new StringBuilder();
		_src.append("{");
		for(String key : body.keySet()) {
			Object val = body.get(key);
			if (val==null) continue;
			assert key.charAt(0) != '"' : key;
			_src.append('"');_src.append(key);_src.append('"');
			_src.append(':');
			// Strings must be JSON already. If you want to pass in a json string (e.g. the script param) -- encode it.
			_src.append(val);			
			_src.append(',');
		}
		StrUtils.pop(_src, 1);
		_src.append("}");
		src = _src.toString();
		return (String) src;
	}
}
