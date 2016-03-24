package com.winterwell.es.query;

import static org.junit.Assert.*;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.junit.Test;

import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.SearchRequestBuilder;
import com.winterwell.utils.containers.ArrayMap;

public class MapQueryBuilderTest {

	@Test
	public void testMapFilterBuilder() {
		MapQueryBuilder mfb = new MapQueryBuilder(new ArrayMap(""));
		// link parent/child to get time & details
		QueryBuilder query = new MapQueryBuilder(new ArrayMap(
				"has_parent", new ArrayMap(
						"type", "evs",
						"query", new ArrayMap("match_all",new ArrayMap()),
						"inner_hits", new ArrayMap("name","base_parent")
						)
				));		
		String q = query.toString();
		System.out.println(q);
	}

}
