package com.winterwell.es.client.admin;

import java.util.HashSet;
import java.util.Set;

import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.es.client.IESResponse;
import com.winterwell.utils.Utils;
import com.winterwell.web.WebEx;

public class GetAliasesRequest extends ESHttpRequest<GetAliasesRequest, IESResponse> {

	public GetAliasesRequest(ESHttpClient hClient) {
		super(hClient, "_alias");
	}

	/**
	 * 
	 * @param getAliasesResponse
	 * @return base indices, or if there was a 404 error (index does not exist), then an empty set.
	 */
	public static Set<String> getBaseIndices(IESResponse getAliasesResponse) {
		if (getAliasesResponse.getError() instanceof WebEx.E404) {
			// 404 => index does not exist -- so no aliases
			return new HashSet();
		}
		getAliasesResponse.check();
		return getAliasesResponse.getParsedJson().keySet();
	}
}
