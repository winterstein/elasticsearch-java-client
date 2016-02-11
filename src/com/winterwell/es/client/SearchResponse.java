package com.winterwell.es.client;

import java.util.List;
import java.util.Map;

import com.winterwell.datalog.Stat;
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
	Double getTotal();


}
