package com.winterwell.es.client.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.TermQueryBuilder;

import com.winterwell.utils.ReflectionUtils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.log.Log;

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
		lockCheck();
		// check for adding non clauses
		if (q instanceof BoolQueryBuilder && ((BoolQueryBuilder) q).isEmpty()) {
			Log.w("ES.bool", "Added non-clause to "+this+" "+ReflectionUtils.getSomeStack(8));
		}
		Map bool = (Map) jobj.get("bool");
		List qs = (List) bool.get(cond);
		if (qs==null) {
			qs = new ArrayList();		
			bool.put(cond, qs);
		}
		// add in json form to avoid mysterious serialisation bugs later
		qs.add(q.toJson2());
	}	

	public BoolQueryBuilder must(ESQueryBuilder q) {
		Map bool = (Map) jobj.get("bool");
		if (q instanceof BoolQueryBuilder) {
			// avoid pointless wrapping
			if (bool.isEmpty()) {
				// add to this bool anyway, 'cos the user might not catch the return object
				ESQueryBuilder q2 = q.clone(); // clone to avoid locking q
				add("must", q2);
				// return unwrapped
				return (BoolQueryBuilder) q;
			}			
		}
		add("must", q);
		return this;
	}
	
	public BoolQueryBuilder mustNot(ESQueryBuilder q) {
		add("must_not", q);
		return this;
	}
	/**
	 * TODO The clause (query) must appear in matching documents. However unlike must the score of the query will be ignored. 
	 * Filter clauses are executed in filter context, meaning that scoring is ignored and clauses are considered for caching.
	 * ref??
	 * 
	 * @param q
	 * @return
	 */
	public BoolQueryBuilder filter(ESQueryBuilder q) {
		add("must", q);
		return this;
	}

	public ESQueryBuilder minimumNumberShouldMatch(int n) {
		lockCheck();
		Map bool = (Map) jobj.get("bool");
		bool.put("minimum_should_match", n);
		return this;
	}

	public boolean isEmpty() {
		Map bool = (Map) jobj.get("bool");		
		return bool.isEmpty();
	}
}
