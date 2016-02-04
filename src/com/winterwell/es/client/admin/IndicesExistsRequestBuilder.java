package com.winterwell.es.client.admin;

import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.es.client.IESResponse;

/**
 * @testedby IndicesExistsRequestBuilderTest
 * @author daniel
 *
 */
public class IndicesExistsRequestBuilder extends ESHttpRequest<IndicesExistsRequestBuilder,IESResponse> {

	public IndicesExistsRequestBuilder(IndicesAdminClient iac) {
		super(iac.hClient);
		method = "HEAD";
	}

}
