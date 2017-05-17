package com.winterwell.es.client;

import java.util.Arrays;

public class DeleteRequestBuilder extends ESHttpRequest<DeleteRequestBuilder, IESResponse> {

	/**
	 * Force a refresh?
	 * See https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-refresh.html
	 * @param string false | true | wait_for
	 */
	public void setRefresh(String refresh) {
		assert Arrays.asList("false","true","wait_for").contains(refresh) : refresh;
		params.put("refresh", refresh);		
	}

	public DeleteRequestBuilder(ESHttpClient hClient) {
		super(hClient);
		method = "DELETE";
	}

}
