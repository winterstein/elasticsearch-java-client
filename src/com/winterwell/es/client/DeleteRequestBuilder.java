package com.winterwell.es.client;

import com.winterwell.web.WebEx;

/**
 * @tested {@link DeleteRequestBuilderTest}
 * @author daniel
 *
 */
public class DeleteRequestBuilder extends ESHttpRequest<DeleteRequestBuilder, IESResponse> {

	private boolean ignoreE404;

	public DeleteRequestBuilder(ESHttpClient hClient) {
		super(hClient, null);
		method = "DELETE";
		setType("_doc"); // the new ESv7 omni-type
	}
	
	@Override
	protected ESHttpResponse doExecute(ESHttpClient esjc) {
		ESHttpResponse res = super.doExecute(esjc);
		if (ignoreE404 && res.getError() instanceof WebEx.E404) {
			// We tried to delete seomthing that didn't exist -- which is OK
			ESHttpResponse res2 = new ESHttpResponse(this, null, null);
			return res2;
		}
		return res;
	}

	public void setIgnoreError404(boolean b) {
		ignoreE404 = b;
	}

}
