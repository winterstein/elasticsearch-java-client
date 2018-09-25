package com.winterwell.es.client;

import java.util.Arrays;

public class DeleteRequestBuilder extends ESHttpRequest<DeleteRequestBuilder, IESResponse> {

	public DeleteRequestBuilder(ESHttpClient hClient) {
		super(hClient, null);
		method = "DELETE";
	}

}
