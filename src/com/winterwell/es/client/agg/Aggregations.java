/**
 * 
 */
package com.winterwell.es.client.agg;

import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;

/**
 * @author daniel
 *
 */
public class Aggregations {

	/**
	 * https://www.elastic.co/guide/en/elasticsearch/reference/current/search-aggregations-bucket-datehistogram-aggregation.html
	 * @param name
	 * @return
	 */
	public static Aggregation dateHistogram(String name, String field) {
		return new Aggregation(name, "date_histogram", field).put("interval", "day");
	}

	public static Aggregation stats(String name, String field) {
		return new Aggregation(name, "stats", field);
	}

}
