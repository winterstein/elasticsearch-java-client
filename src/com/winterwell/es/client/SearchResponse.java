package com.winterwell.es.client;

import java.util.List;
import java.util.Map;

import com.winterwell.datalog.DataLog;
import com.winterwell.es.client.agg.AggregationResults;
import com.winterwell.web.WebEx;

public interface SearchResponse extends IESResponse {

	/**
	 * @return List of hits, which are wrapper objects around a _source document.
	 * @throws WebEx if the search failed.
	 */
	List<Map> getHits();

	Map getFacets();

	
	
	/**
	 * The initial search request and each subsequent scroll request returns a new scroll_id.
	 * <b>Only the most recent scroll_id should be used.</b>
	 * @return the new scroll_id for the next request
	 */
	String getScrollId();

	/**
	 * @return the total possible number results which could be returned for query, 
	 * not necessarily the amount which WILL be returned (See ResultsPerPage)
	 * 
	 */
	long getTotal();

	Map getAggregations();

	List<Map<String,Object>> getSearchResults();
	
	<X> List<X> getSearchResults(Class<? extends X> klass);

	AggregationResults getAggregationResults(String name);

	/**
	 * @param name the Suggester's name
	 * @return similar to {@link #getHits()}, _source gives the documents
	 */
	List<Map> getSuggesterHits(String name);

}
