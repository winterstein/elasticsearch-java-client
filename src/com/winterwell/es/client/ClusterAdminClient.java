package com.winterwell.es.client;

public class ClusterAdminClient {

	private ESHttpClient hClient;

	public ClusterAdminClient(ESHttpClient esHttpClient) {
		this.hClient = esHttpClient;
	}

}
