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

	/**
	 * Stats on a numeric field
	 * @param name
	 * @param field This MUST be numeric
	 * @return
	 */
	public static Aggregation stats(String name, String field) {
		return new Aggregation(name, "stats", field);
	}

	/**
	 * document count by field=value,
	 * If you want to sum a numerical value from the documents, use this with a stats() subAggregation
	 * @param name
	 * @param field usually a keyword field
	 * @return
	 */
	public static Aggregation terms(String name, String field) {
		return new Aggregation(name, "terms", field);
	}
	
	public static Aggregation significantTerms(String name, String field) {
		return new Aggregation(name, "significant_terms", field);
	}	
}
