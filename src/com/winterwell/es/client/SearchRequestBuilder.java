package com.winterwell.es.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.util.ajax.JSON;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import com.winterwell.utils.StrUtils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.time.Dt;
import com.winterwell.utils.time.TUnit;

/**
 * @see org.elasticsearch.action.search.SearchRequestBuilder
 * @author daniel
 *
 */
public class SearchRequestBuilder extends ESHttpRequestWithBody<SearchRequestBuilder,SearchResponse> {


	public SearchRequestBuilder(ESHttpClient hClient) {
		super(hClient);
		endpoint = "_search";		
	}


    /**
     * The document types to execute the search against. Defaults to be executed against
     * all types.
     */
	public SearchRequestBuilder setTypes(String... types) {
		assert types.length==1 : "TODO";
		setType(types[0]);
		return this;
	}


	/**
	 * See https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-search-type.html
	 * @param searchType
	 * @return
	 */
	public SearchRequestBuilder setSearchType(SearchType searchType) {
		return setSearchType(searchType.toString().toLowerCase());
	}
	
	/**
	 * See https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-search-type.html
	 * @param searchType e.g. "scan" (although that was deprecated in 2.1)
	 * @return
	 */
	public SearchRequestBuilder setSearchType(String searchType) {
		params.put("search_type", searchType);
		return this;
	}


	public SearchRequestBuilder setQuery(QueryBuilder qb) {
		body.put("query", qb.toString());
		return this;
	}
	
//	/**
//	 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-filtered-query.html#_filtering_without_a_query
//	 * @param fb
//	 */
//	public SearchRequestBuilder setQuery(Object FilterBuilderfb) {
////		filtered = "filtered: {filter: "+fb.toString()+"}";
//		BoolQueryBuilder q = QueryBuilders.boolQuery().m filteredQuery(QueryBuilders.matchAllQuery(), FilterBuilderfb);
////		body.put("query", filtered);
//		setQuery(q);
//		return this;
//	}


	public SearchRequestBuilder setFrom(int i) {
		params.put("from", i);
		return this;
	}
	/**
	 * How many results to fetch. The default is 10.
	 * @param n 
	 * @return this
	 */
	public SearchRequestBuilder setSize(int n) {
		params.put("size", n);
		return this;
	}


	public SearchRequestBuilder addSort(SortBuilder sort) {
		List sorts = (List) body.get("sort");
		if (sorts==null) {
			sorts = new ArrayList();
			body.put("sort", sorts);
		}
		// HACK correct the toString from ES
		// TODO Better!!
		String ss = sort.toString();
		ss = "{"+ss.replace("\"{", "\": {") +"}";
		assert JSON.parse(ss) != null;
		sorts.add(ss);
		return this;
	}
	
	/**
	 * See https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-sort.html
	 * @param sort
	 * @return
	 */
	public SearchRequestBuilder setSort(String sort) {
		List sorts = (List) body.get("sort");
		if (sorts==null) {
			sorts = new ArrayList();
			body.put("sort", sorts);
		}
		sorts.clear();
		sorts.add("\""+sort+"\"");
		return this;
	}

//	public SearchRequestBuilder addFacet(FacetBuilder field) {
//		// TOTAL HACK
//		org.elasticsearch.action.search.SearchRequestBuilder srb = new org.elasticsearch.action.search.SearchRequestBuilder(new DummyClient());
//		srb.addFacet(field);
//		String srbs = srb.toString();
//		Map map = hClient.gson.fromJson(srbs, Map.class);
//		Object facets = map.get("facets");
//		body.put("facets", hClient.gson.toJson(facets));
//		return this;
//	}



	public void addSort(String field, SortOrder order) {
		addSort(SortBuilders.fieldSort(field).order(order));
	}

	/**
	 * How long to keep scroll resources open between requests.
	 * NB: Scroll is typically used with setSort("_doc");
	 * 
	 * @link https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-scroll.html
	 *  
	 * @param keepAlive
	 *  
	 */
	public void setScroll(TimeValue keepAlive) {
		// lean on TimeValue.toString() fitting the right format
		params.put("scroll", keepAlive);
	}
	/**
	 * How long to keep scroll resources open between requests.
	 * NB: Scroll is typically used with setSort("_doc");
	 * 
	 * @link https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-scroll.html
	 * @param keepAlive
	 * @see SearchScrollRequestBuilder
	 */
	public void setScroll(Dt keepAlive) {
		int s = (int) keepAlive.convertTo(TUnit.SECOND).getValue();
		setScroll(TimeValue.timeValueSeconds(s));
	}
}
