package com.winterwell.es.client;

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
