package com.winterwell.es.client.admin;

import com.winterwell.es.ESType;
import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.es.client.IESResponse;
import com.winterwell.utils.Utils;

/**
 * Ref: https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html
 * See {@link ESType}
 * @author daniel
 * @testedby {@link PutMappingRequestBuilderTest}
 */
public class PutMappingRequestBuilder extends ESHttpRequest<PutMappingRequestBuilder,IESResponse> {
	
	
	public PutMappingRequestBuilder(ESHttpClient hClient, String idx, String type) {
		super(hClient, "_mapping");
		setIndex(idx);
		method = "PUT";
		setType(type); //null); // no mapping types in ESv7
	}

	public PutMappingRequestBuilder setMapping(ESType type) {
		body().putAll(type);
		return this;
	}

}
