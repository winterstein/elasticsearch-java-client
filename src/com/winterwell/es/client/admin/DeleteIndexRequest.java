package com.winterwell.es.client.admin;

import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.es.client.IESResponse;

public class DeleteIndexRequest extends ESHttpRequest<DeleteIndexRequest, IESResponse>{

	public DeleteIndexRequest(ESHttpClient hClient, String... indices) {
		super(hClient);
		method = "DELETE";
		setIndices(indices);
	}

}
