package com.winterwell.es.client.admin;

import java.util.Map;

import com.winterwell.utils.Utils;

import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.es.client.IESResponse;
import com.winterwell.utils.containers.ArrayMap;

/**
 * See https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html
 * 
 * @author daniel
 *
 */
public class PutIndexTemplateRequest extends ESHttpRequest<IndicesExistsRequestBuilder,IESResponse> {	
	
	public PutIndexTemplateRequest(ESHttpClient hClient, String templatePattern) {
		super(hClient);
		endpoint = "/_template";
		method = "PUT";
		ArrayMap msrc = new ArrayMap();
		setSource(msrc);
		msrc.put("template", templatePattern);
		assert templatePattern.contains("*");
	}
	
	public PutIndexTemplateRequest addMapping(String type, Map mapping) {
		assert ! type.isEmpty();
		Map mappings = (Map) body.get("mappings");
		if (mappings==null) {
			mappings = new ArrayMap();
			body.put("mappings", mappings);
		}
		mappings.put(type, mapping);
		return this;
	}

}
