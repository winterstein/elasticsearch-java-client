package com.winterwell.es.client.query;

import java.util.Map;

import org.elasticsearch.index.query.QueryBuilder;

import com.winterwell.es.ESUtils;
import com.winterwell.utils.web.IHasJson;

/**
 * Base class for query-builders. For common cases use the convenience methods in {@link ESQueryBuilders}
 * @author daniel
 *
 */
public class ESQueryBuilder implements IHasJson {

	@Override
	public String toString() {	
		return "ESQueryBuilder"+toJSONString();
	}
	
	Map jobj;

	public ESQueryBuilder(Map query) {
		this.jobj = query;
	}
	
	public ESQueryBuilder(QueryBuilder query) {
		this.jobj = ESUtils.jobj(query);
	}

	/**
	 * Construct an ESQueryBuilder from a QueryBuilder, ESQueryBuilder, or Map.
	 * 
	 * Note: the input object should not be modified afterwards!
	 *  
	 * @param query_mapOrQueryBuilder
	 * @return
	 */
	public static ESQueryBuilder make(Object query_mapOrQueryBuilder) {
		if (query_mapOrQueryBuilder instanceof ESQueryBuilder) {
			return (ESQueryBuilder) query_mapOrQueryBuilder;
		}
		if (query_mapOrQueryBuilder instanceof QueryBuilder) {
			return new ESQueryBuilder((QueryBuilder)query_mapOrQueryBuilder);
		}
		return new ESQueryBuilder((Map)query_mapOrQueryBuilder);
	}

	@Override
	public Map<String,Object> toJson2() throws UnsupportedOperationException {
		return jobj;
	}
}
