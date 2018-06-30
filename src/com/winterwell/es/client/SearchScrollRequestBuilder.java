package com.winterwell.es.client;

import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.util.concurrent.EsRejectedExecutionException;

import com.winterwell.utils.StrUtils;
import com.winterwell.utils.time.Dt;
import com.winterwell.utils.time.TUnit;
import com.winterwell.utils.web.WebUtils;

/**
 * 
 * https://www.elastic.co/guide/en/elasticsearch/reference/6.2/search-request-scroll.html
 * 
 * @see org.elasticsearch.action.search.SearchScrollRequestBuilder
 * @author daniel
 *
 */
public class SearchScrollRequestBuilder extends ESHttpRequest<SearchScrollRequestBuilder, SearchResponse> {

	/**
	 * 
	 * @param esHttpClient
	 * @param scrollId
	 * @param scrollWindow How long to keep the scroll open for until the next request. Can be null (in which case this request will close the scroll).
	 * Suggested value: 1 minute
	 */
	public SearchScrollRequestBuilder(ESHttpClient esHttpClient, String scrollId, Dt scrollWindow) {
		super(esHttpClient, "_search/scroll");
		params.put("scroll_id", scrollId);
		// You must keep setting a scroll window to keep the scroll alive.
		// This is such a gotcha, that we'll make the user set it here.
		setScroll(scrollWindow);
		method = "POST";
		setIndices(); // no index - it comes from the scroll id
	}
	
	@Override
	StringBuilder getUrl(String server) {
		// no indices
		StringBuilder url = new StringBuilder(server);
		url.append("/"+endpoint);				
		return url;
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
		params.put("scroll", s+"s");
	}
	
}
