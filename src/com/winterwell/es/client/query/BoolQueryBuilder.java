package com.winterwell.es.client.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.TermQueryBuilder;

import com.winterwell.utils.containers.ArrayMap;

/**
 * See https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-bool-query.html
 * @author daniel
 *
 */
public class BoolQueryBuilder extends ESQueryBuilder {

	public BoolQueryBuilder() {
		this(new ArrayMap("bool", new ArrayMap()));		
	}
	
	public BoolQueryBuilder(Map query) {
		super(query);
	}

	public BoolQueryBuilder should(ESQueryBuilder q) {
		add("should", q);
		return this;
	}
	private void add(String cond, ESQueryBuilder q) {
		Map bool = (Map) jobj.get("bool");
		List qs = (List) bool.get(cond);
		if (qs==null) {
			qs = new ArrayList();		
			bool.put(cond, qs);
		}
		qs.add(q);
	}

	public BoolQueryBuilder must(ESQueryBuilder q) {
		add("must", q);
		return this;
	}
	
	public BoolQueryBuilder mustNot(ESQueryBuilder q) {
		add("must_not", q);
		return this;
	}
	/**
	 * The clause (query) must appear in matching documents. However unlike must the score of the query will be ignored. 
	 * Filter clauses are executed in filter context, meaning that scoring is ignored and clauses are considered for caching.
	 * 
	 * @param q
	 * @return
	 */
	public BoolQueryBuilder filter(ESQueryBuilder q) {
		add("must", q);
		return this;
	}

	public ESQueryBuilder minimumNumberShouldMatch(int n) {
		Map bool = (Map) jobj.get("bool");
		bool.put("minimum_should_match", n);
		return this;
	}
}
