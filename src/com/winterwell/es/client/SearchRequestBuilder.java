package com.winterwell.es.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.util.ajax.JSON;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;

import com.winterwell.es.ESUtils;
import com.winterwell.es.client.agg.Aggregation;
import com.winterwell.es.client.agg.Aggregations;
import com.winterwell.es.client.query.ESQueryBuilder;
import com.winterwell.es.client.query.ESQueryBuilders;
import com.winterwell.es.client.sort.Sort;
import com.winterwell.es.client.suggest.Suggester;
import com.winterwell.gson.RawJson;
import com.winterwell.utils.StrUtils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.log.Log;
import com.winterwell.utils.time.Dt;
import com.winterwell.utils.time.TUnit;
import com.winterwell.utils.web.SimpleJson;

/**
 * @testedby SearchRequestBuilderTest
 * @see org.elasticsearch.action.search.SearchRequestBuilder
 * @author daniel
 *
 */
public class SearchRequestBuilder extends ESHttpRequest<SearchRequestBuilder,SearchResponse> {


	/**
	 * @param excluded Can use wildcards, e.g. "*.bloat"
	 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/docs-get.html#get-source-filtering
	 * @return 
	 */
	public SearchRequestBuilder setResultsSourceExclude(String... excluded) {
		params.put("_source_exclude", StrUtils.join(excluded, ","));
		return this;
	}
	/**
	 * @param included Can use wildcards, e.g. "*.bloat"
	 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/docs-get.html#get-source-filtering
	 * @return 
	 */
	public SearchRequestBuilder setResultsSourceInclude(String... included) {
		params.put("_source_include", StrUtils.join(included, ","));
		return this;
	}


	public SearchRequestBuilder(ESHttpClient hClient) {
		super(hClient, "_search");
		// what method is it?? probably post for the body 
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

	/**
	 * Best practice: Use this to set the query. / filter.
	 * @param qb Cannot be modified afterwards.
	 * @return
	 */
	public SearchRequestBuilder setQuery(ESQueryBuilder qb) {
		return setQuery(qb.toJson2());
	}
	/**
	 * Convenience method for building up AND queries.
	 * This will set the query if null, or combine with bool-query *must* if not null.
	 * 
	 * @see #setQuery(ESQueryBuilder)
	 * 
	 * @param qb
	 * @return 
	 */
	public SearchRequestBuilder addQuery(ESQueryBuilder qb) {
		Map query = (Map) body().get("query");
		if (query==null) {
			setQuery(qb);
			return this;
		}
		// Add to it
		// Is it a boolean?
//		String qtype = (String) Containers.first(query.keySet());
//		if (qtype != "bool") {
			ESQueryBuilder qand = ESQueryBuilders.must(query, qb);
			setQuery(qand.toJson2());
//		} else {
			// TODO merge!			
//		}
		return this;
	}
	

	public SearchRequestBuilder setQuery(Map queryJson) {
		body().put("query", queryJson);
		return this;
	}
	
	public SearchRequestBuilder setFrom(int i) {
		params.put("from", i);
		return this;
	}
	/**
	 * How many results to fetch. The default is 10.
	 * Important: If this is used in a scroll -- this is the *batch size*!
	 * @param n 
	 * @return this
	 */
	public SearchRequestBuilder setSize(int n) {
		params.put("size", n);
		return this;
	}

	public SearchRequestBuilder addSort(Sort sort) {
		// type Map (normal) | String (ScoreSort)
		List sorts = (List) body().get("sort");
		if (sorts==null) {
			sorts = new ArrayList();
			body().put("sort", sorts);
		}		
		sorts.add(sort.toJson2());
		return this;
	}
	
	/**
	 * Really just an aide-memoire for {@link #addSort(Sort)}.
	 * Differs in that being a `set` it will overwrite any existing value.
	 * @param sort
	 */
	public SearchRequestBuilder setSort(Sort sort) {
		List<Map> sorts = (List) body().get("sort");
		if (sorts!=null) {
			sorts.clear();
		}
		return addSort(sort);		

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
		params.put("scroll", s+"s");
	}


	/**
	 * See {@link Aggregations}
	 * Note: If you only want the aggregation results and not the documents, set size-0 with {@link #setSize(int)}.
	 * @return this
	 */
	public SearchRequestBuilder addAggregation(Aggregation dh) {
		// NB: This is copy pasta Aggregation.subAggregation()
		Map sorts = (Map) body().get("aggs");
		if (sorts==null) {
			sorts = new ArrayMap();
			body.put("aggs", sorts);
		}
		// e.g.      "grades_stats" : { "stats" : { "field" : "grade" } }
		Object noOld = sorts.put(dh.name, dh);
		// safety check
		if (noOld != null) {
			if (noOld==dh) {
				Log.w("search", "2x aggregation "+dh.name);
			} else {
				throw new IllegalStateException("Duplicate named aggregations: "+dh.name+" "+noOld+" vs "+dh);
			}
		}
		return this;		
	}
	
	/**
	 * See {@link Suggesters}
	 * @return this
	 */
	public SearchRequestBuilder addSuggester(Suggester suggester) {
		// NB: This is copy pasta Aggregation.subAggregation()
		Map sorts = (Map) body().get("suggest");
		if (sorts==null) {
			sorts = new ArrayMap();
			body.put("suggest", sorts);
		}
		sorts.put(suggester.name, suggester); //.toJson2()); // TODO support late json conversion
		// but caused a bug -- why is this behaving differently to Aggregation??
		return this;		
	}
	
	/**
	 * 
	 * @return Can be null (unset => 10)
	 */
	public Integer getSize() {
		Number n = (Number) params.get("size");
		return n==null? null : n.intValue();
	}
	
}
