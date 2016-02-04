package com.winterwell.es.client;

import java.util.Map;

/**
 * Delete a mapping (schema) AND THE DATA.
 * @author daniel
 *
 */
public class DeleteMappingRequestBuilder extends ESHttpRequest<DeleteMappingRequestBuilder,IESResponse> {

	public DeleteMappingRequestBuilder(ESHttpClient hClient, String... indices) {
		super(hClient);
		setIndices(indices);		
		method = "DELETE";
		endpoint = "_mapping";
	}


}
