package com.winterwell.es.client.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.winterwell.utils.ReflectionUtils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.log.Log;

/**
 * See https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-bool-query.html
 * @author daniel
 *
 */
public class BoolQueryBuilder extends ESQueryBuilder {

	private static final String must = "must";

	public BoolQueryBuilder() {
		this(new ArrayMap("bool", new ArrayMap()));		
	}
	
	public BoolQueryBuilder(Map query) {
		super(query);
	}

	/**
	 * aka OR
	 * @param q
	 * @return this
	 */
	public BoolQueryBuilder should(ESQueryBuilder q) {
		add("should", q);
		return this;
	}
	
	/**
	 * Add an extra condition
	 * @param cond
	 * @param q NB: This will get converted to json here and now. It cannot then be modified.
	 */
	private void add(String cond, ESQueryBuilder q) {
		lockCheck();
		// check for adding non clauses
		if (q instanceof BoolQueryBuilder && ((BoolQueryBuilder) q).isEmpty()) {
			Log.w("ES.bool", "Added non-clause to "+this+" "+ReflectionUtils.getSomeStack(8));
		}
		List qs = (List) props.get(cond);
		if (qs==null) {
			qs = new ArrayList();		
			props.put(cond, qs);
		}
		// add in json form to avoid mysterious serialisation bugs later
		qs.add(q.toJson2());
	}	

	public BoolQueryBuilder must(ESQueryBuilder q) {
		// we'd like to avoid pointless wrapping of boolquerybuilders
		// -- but we can't do that in here
		// 'cos e.g. must(a or b).must(c) would end up as should(a or b).must(c)
		// if we don't wrap that first or.
		// if (q instanceof BoolQueryBuilder) {
//			// avoid pointless wrapping
//			if (props.isEmpty()) {
//				// add to this bool anyway, 'cos the user might not catch the return object
//				ESQueryBuilder q2 = q.clone(); // clone to avoid locking q ??shouldn't we lock it?
//				add(must, q2);
//				// but return the unwrapped original - hopefully they'll use that
//				return (BoolQueryBuilder) q;
//			}			
//		}
		add(must, q);
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
		add(must, q);
		return this;
	}

	public BoolQueryBuilder minimumNumberShouldMatch(int n) {
		lockCheck();
		props.put("minimum_should_match", n);
		return this;
	}

	public boolean isEmpty() {
		return props.isEmpty();
	}

//	/** Probably better to avoid making the wrapping
//	 * @return a single must? then unwrap it. otherwise this
//	 */
//	public ESQueryBuilder simplify() {
//		if (props.size() == 1) {
//			List must1 = (List) props.get(must);
//			if (must1 != null && must1.size() == 1) {
//				Map m = must1.get(0);
//				return new ESQueryBuilder(m);
//			}
//		}
//		return this;
//	}
}
