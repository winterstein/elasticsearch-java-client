package com.winterwell.es.client;

public class DeleteIndexRequest extends ESHttpRequest<DeleteIndexRequest, IESResponse>{

	public DeleteIndexRequest(ESHttpClient hClient, String... indices) {
		super(hClient);
		method = "DELETE";
		setIndices(indices);
	}

}
