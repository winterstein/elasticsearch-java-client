package com.winterwell.es.client.admin;

import java.util.Map;

import com.winterwell.es.ESType;
import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.es.client.IESResponse;

/**
 * Ref: https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html
 * See {@link ESType}
 * @author daniel
 *
 */
public class PutMappingRequestBuilder extends ESHttpRequest<PutMappingRequestBuilder,IESResponse> {

	public PutMappingRequestBuilder(ESHttpClient hClient, String idx, String type) {
		super(hClient);
		setIndex(idx);
		method = "PUT";
		endpoint = "_mapping";
		setType(type);
	}


}
