package com.winterwell.es.client;

import java.util.List;

import com.winterwell.utils.StrUtils;
import winterwell.utils.TodoException;

public class ClearScrollRequestBuilder extends ESHttpRequest<ClearScrollRequestBuilder, IESResponse> {

	public ClearScrollRequestBuilder(ESHttpClient esHttpClient) {
		super(esHttpClient);
		method = "DELETE";
		endpoint = "_search/scroll";
	}

	public void setScrollIds(List<String> asList) {
		params.put("scroll_id", StrUtils.join(asList, ","));
	}

}
