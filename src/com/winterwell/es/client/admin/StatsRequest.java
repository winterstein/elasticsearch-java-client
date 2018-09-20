package com.winterwell.es.client.admin;

import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.es.client.ESHttpResponse;

/**
 * Get index stats
 * @author daniel
 *
 */
public class StatsRequest extends ESHttpRequest<StatsRequest,StatsResponse> {

	public StatsRequest(ESHttpClient hClient) {
		super(hClient, null);
//		method = "HEAD";
		setIndex("_stats");
	}

	@Override
	protected StatsResponse processResponse(ESHttpResponse response) {
		return new StatsResponse(response);
	}
}
