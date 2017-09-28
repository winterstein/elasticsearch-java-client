package com.winterwell.es.client.admin;

import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.es.client.ESHttpResponse;
import com.winterwell.es.client.IESResponse;

public class IndexSettingsRequest extends ESHttpRequest<IndexSettingsRequest, IESResponse> {

	public IndexSettingsRequest(ESHttpClient hClient) {
		super(hClient, "_settings");
	}

}
