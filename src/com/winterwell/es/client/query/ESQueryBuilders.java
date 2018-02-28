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
	 * @param queries Can be Maps, QueryBuilder objects, or ESQueryBuilder objects. Can contain nulls
	 * If this is a single query, the same query will be returned.
	 * @return and all the input queries. null if all inputs were null.
	 */
	public static ESQueryBuilder must(Object... queries) {
		// filter out nulls
		List queryList = Containers.filterNulls(Arrays.asList(queries));
		if (queryList.isEmpty()) {
			return null;
		}
		// standardise
		List<ESQueryBuilder> esqs = Containers.apply(queryList, ESQueryBuilder::make);
		if (queryList.size()==1) return esqs.get(0);
		// combine
		List<Map> maps = Containers.apply(esqs, esq -> esq.toJson2());
		Map must = new ArrayMap("bool", new ArrayMap("must", maps));
		return new ESQueryBuilder(must);
	}

	/**
	 * Find exact term matches. 
	 * 
	 * Note: When querying full text fields, use the match query instead, which understands how the field has been analyzed.
	 * 
	 * https://www.elastic.co/guide/en/elasticsearch/reference/6.2/query-dsl-term-query.html
	 * @param field
	 * @param value
	 * @return
	 */
	public static ESQueryBuilder termQuery(String field, String value) {
		Map must = new ArrayMap("term", new ArrayMap(field, value));
		return new ESQueryBuilder(must);
	}

	public static BoolQueryBuilder boolQuery() {
		return new BoolQueryBuilder();
	}

}
