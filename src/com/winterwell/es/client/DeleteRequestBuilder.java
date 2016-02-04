package com.winterwell.es.client;

public class DeleteRequestBuilder extends ESHttpRequest<DeleteRequestBuilder, IESResponse> {

	public DeleteRequestBuilder(ESHttpClient hClient) {
		super(hClient);
		method = "DELETE";
	}

}
