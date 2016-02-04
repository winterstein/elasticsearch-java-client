package com.winterwell.es.client;

import java.util.Map;

public class PutMappingRequestBuilder extends ESHttpRequest<PutMappingRequestBuilder,IESResponse> {

	public PutMappingRequestBuilder(ESHttpClient hClient, String idx) {
		super(hClient);
		setIndex(idx);
		method = "PUT";
		endpoint = "_mapping";
	}


}
