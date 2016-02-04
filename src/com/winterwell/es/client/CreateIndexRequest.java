package com.winterwell.es.client;

import java.util.Map;

import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.web.SimpleJson;

public class CreateIndexRequest extends ESHttpRequest<CreateIndexRequest,IESResponse> {

	public CreateIndexRequest(ESHttpClient hClient, String index) {
		super(hClient);
		setIndex(index);
//		endpoint; Just do a put to the index url
		method = "PUT";
	}

	/**
	 * 
	 * @param analyzer e.g. keyword See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/analysis-analyzers.html
	 * @return 
	 */
	public CreateIndexRequest setDefaultAnalyzer(String analyzer) {
		if (src==null) setSource(new ArrayMap());
		SimpleJson.set((Map)src, analyzer, "index", "analysis", "analyzer", "default", "type");
		return this;
	}

}
