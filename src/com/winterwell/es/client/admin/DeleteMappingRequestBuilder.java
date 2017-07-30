package com.winterwell.es.client.admin;

import java.util.Map;

import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.es.client.IESResponse;

/**
 * Delete a mapping (schema) AND THE DATA.
 * @author daniel
 *
 */
public class DeleteMappingRequestBuilder extends ESHttpRequest<DeleteMappingRequestBuilder,IESResponse> {

	public DeleteMappingRequestBuilder(ESHttpClient hClient, String... indices) {
		super(hClient, "_mapping");
		setIndices(indices);		
		method = "DELETE";
	}


}
