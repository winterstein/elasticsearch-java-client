package com.winterwell.es.client.admin;

import com.winterwell.es.client.ESHttpClient;

public class ClusterAdminClient {

	private ESHttpClient hClient;

	public ClusterAdminClient(ESHttpClient esHttpClient) {
		this.hClient = esHttpClient;
	}

}
