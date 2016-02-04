package com.winterwell.es.client.admin;

import java.util.Map;

import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.es.client.ESHttpResponse;
import com.winterwell.es.client.IESResponse;

public class StatsResponse extends ESHttpResponse {

	public StatsResponse(ESHttpResponse response) {
		super(response);
	}

	public Map getIndices() {
		return (Map) getParsedJson().get("indices");
	}
}
