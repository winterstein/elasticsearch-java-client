package com.winterwell.es.client.query;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.winterwell.utils.MathUtils;
import com.winterwell.utils.TodoException;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.containers.Containers;
import com.winterwell.utils.time.Time;

/**
 * Convenience utils
 * @author daniel
 *
 */
public class ESQueryBuilders {

	/**
	 * Convenience for a shared value that indicates unset/undefined. This is NOT part of the ElasticSearch API itself.
	 */
	public static final String UNSET = "unset";

	/**
	 * https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-match-all-query.html
	 * @return
	 */
	public static ESQueryBuilder matchAll() {
//		match_all
		throw new TodoException();
	}
	
	/**
	 * Combine several ES queries via bool.must (i.e. AND).
	 * @param queries Can be Maps, QueryBuilder objects, or ESQueryBuilder objects. Can contain nulls.
	 * If there is a single non-null query, the same query will be returned (i.e. no superfluous bool wrapper is added).
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
		// just one?
		if (queryList.size()==1) {
			return esqs.get(0);
		}
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
	public static ESQueryBuilder termQuery(String field, Object value) {
		Map must = new ArrayMap("term", new ArrayMap(field, value));
		return new ESQueryBuilder(must);
	}
	
	public static ESQueryBuilder dateRangeQuery(String field, Time start, Time end) {
		assert field != null;
		if (start==null && end==null) throw new NullPointerException("Must provide one of start/end");
		if (start !=null && end !=null && ! end.isAfter(start)) {
			throw new IllegalArgumentException("Empty range :"+start+" to "+end);
		}
		Map rq = new ArrayMap();
		if (start!=null) {
			rq.put("from", start.toISOString());
			rq.put("include_lower", true);
		}
		if (end!=null) {
			rq.put("to", end.toISOString());
			rq.put("include_upper", true);
		}
		Map must = new ArrayMap("range", 
				new ArrayMap(field, rq));
		return new ESQueryBuilder(must);
	}

	public static BoolQueryBuilder boolQuery() {
		return new BoolQueryBuilder();
	}

	/**
	 * 
	 * Note: to test for a missing field, use this inside {@link BoolQueryBuilder#mustNot(ESQueryBuilder)}
	 * 
	 * https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-range-query.html
	 * 
	 * @param field
	 * @return true if one or more non-null values are present for this field
	 */
	public static ESQueryBuilder existsQuery(String field) {
		Map must = new ArrayMap("exists", new ArrayMap("field", field));
		return new ESQueryBuilder(must);
	}

	/**
	 * @param field
	 * @param min > this Can be null if max is set.
	 * @param max < this Can be null if min is set.
	 * @param inclusive If true, use <= and >=.
	 * @return
	 */
	public static ESQueryBuilder rangeQuery(String field, Number min, Number max, boolean inclusive) {
		assert field != null;
		if (min==null && max==null) throw new NullPointerException("Must provide one of min/max");
		if (min !=null && max !=null && MathUtils.compare(min, max) != -1) {
			throw new IllegalArgumentException("Empty range for "+field+": "+min+" to "+max);
		}
		Map rq = new ArrayMap();
		if (min!=null) {
			rq.put(inclusive? "gte" : "gt", min);
		}
		if (max!=null) {
			rq.put(inclusive? "lte" : "lt", max);
		}
		Map must = new ArrayMap("range", 
				new ArrayMap(field, rq));
		return new ESQueryBuilder(must);
	}

	public static MoreLikeThisQueryBuilder similar(String like, List<String> fields) {		    
		return new MoreLikeThisQueryBuilder(like).setFields(fields);
	}

}
