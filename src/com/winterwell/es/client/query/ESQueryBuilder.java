package com.winterwell.es.client.query;

import java.util.Map;

import org.elasticsearch.index.query.QueryBuilder;

import com.winterwell.es.ESUtils;
import com.winterwell.utils.web.IHasJson;

public class ESQueryBuilder implements IHasJson {

	Map jobj;

	public ESQueryBuilder(Map query) {
		this.jobj = query;
	}

	public static ESQueryBuilder make(Object query_mapOrQueryBuilder) {
		if (query_mapOrQueryBuilder instanceof ESQueryBuilder) {
			return (ESQueryBuilder) query_mapOrQueryBuilder;
		}
		if (query_mapOrQueryBuilder instanceof QueryBuilder) {
			query_mapOrQueryBuilder = ESUtils.jobj((QueryBuilder)query_mapOrQueryBuilder);
		}
		return new ESQueryBuilder((Map)query_mapOrQueryBuilder);		
	}

	@Override
	public Map<String,Object> toJson2() throws UnsupportedOperationException {
		return jobj;
	}
}
