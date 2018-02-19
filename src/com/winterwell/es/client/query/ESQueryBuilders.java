package com.winterwell.es.client.query;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.winterwell.utils.TodoException;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.containers.Containers;

public class ESQueryBuilders {

	public static Map queryStringQuery(String q) {
		// TODO Auto-generated method stub
		throw new TodoException();
	}

	/**
	 * Combine several ES queries via bool.must (i.e. AND).
	 * @param queries Can be Maps, QueryBuilder objects, or ESQueryBuilder objects.
	 * If this is a single query, the same query will be returned.
	 * @return and all the input queries
	 */
	public static ESQueryBuilder must(Object... queries) {
		// standardise
		List<ESQueryBuilder> esqs = Containers.apply(queries, ESQueryBuilder::make);
		if (queries.length==1) return esqs.get(0);		
		// combine
		List<Map> maps = Containers.apply(esqs, esq -> esq.toJson2());
		Map must = new ArrayMap("bool", new ArrayMap("must", maps));
		return new ESQueryBuilder(must);
	}

}
