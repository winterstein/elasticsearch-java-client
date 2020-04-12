package com.winterwell.es.client.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.winterwell.utils.ReflectionUtils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.log.Log;

/**
 * https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-boosting-query.html
 * @author daniel
 *
 */
public class BoostingQueryBuilder extends ESQueryBuilder {

	public BoostingQueryBuilder() {
		super(new ArrayMap("boosting", new ArrayMap("negative_boost", 0.5)));
		positive(ESQueryBuilders.match_none());
		negative(ESQueryBuilders.match_none());
	}
		
	public BoostingQueryBuilder positive(ESQueryBuilder q) {
		set("positive", q);
		return this;
	}
	
	public BoostingQueryBuilder negative(ESQueryBuilder q) {
		set("negative", q);
		return this;
	}
	
	/**
	 * Add an extra condition
	 * @param cond
	 * @param q NB: This will get converted to json here and now. It cannot then be modified.
	 */
	private void set(String cond, ESQueryBuilder q) {
		lockCheck();
		// check for adding non clauses (probably a bug!)
		if (q instanceof BoostingQueryBuilder && ((BoostingQueryBuilder) q).isEmpty()) {
			Log.w("ES.bool", "Added non-clause to "+this+" "+ReflectionUtils.getSomeStack(8));
		}		
		// add in json form to avoid mysterious serialisation bugs later
		props.put(cond, q.toJson2());
	}	

	
	public boolean isEmpty() {
		return props.isEmpty();
	}
	
}
