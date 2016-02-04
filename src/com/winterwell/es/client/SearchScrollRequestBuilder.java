package com.winterwell.es.client;

import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.util.concurrent.EsRejectedExecutionException;

import winterwell.utils.time.Dt;
import winterwell.utils.time.TUnit;

/**
 * @see org.elasticsearch.action.search.SearchScrollRequestBuilder
 * @author daniel
 *
 */
public class SearchScrollRequestBuilder extends ESHttpRequest<SearchScrollRequestBuilder, SearchResponse> {

	public SearchScrollRequestBuilder(ESHttpClient esHttpClient, String scrollId) {
		super(esHttpClient);
		endpoint = "_search/scroll";
		params.put("scroll_id", scrollId);
	}
	
	/**
	 * The size parameter controls the number of results per shard, not per request, so a size of 10 which hits 5 shards 
	 * will return a maximum of 50 results per scroll request.
	 * @param n
	 */
	public SearchScrollRequestBuilder setSize(int n) {
		params.put("size", n);
		return this;
	}

	
	@Override
	public SearchScrollRequestBuilder setIndex(String idx) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public SearchScrollRequestBuilder setType(String type) {
		throw new UnsupportedOperationException();
	}

	public void setScroll(TimeValue keepAlive) {
		params.put("scroll", keepAlive);
	}
	public void setScroll(Dt keepAlive) {
		int s = (int) keepAlive.convertTo(TUnit.SECOND).getValue();
		setScroll(TimeValue.timeValueSeconds(s));
	}
	
}
