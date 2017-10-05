/**
 * 
 */
package com.winterwell.es.client.agg;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.search.aggregations.AggregationBuilders;

import com.winterwell.utils.containers.ArrayMap;

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

	/**
	 * Can ES output be simplified??
	 * @param aggResult
	 * @return
	 */
	public static Map<String,Object> simplify(Map<String,Object> aggResult) {
//		Map<String, Object> simpler = new ArrayMap();
//		// from {buckets -> {key, doc_count by_time -> sub-agg}} 
//		List<Map> buckets = (List) aggResult.get("buckets");
//		if (buckets==null) {
//			
//		}
//		for (Map<String,Object> bucket : buckets) {
//			String key = (String) bucket.get("key_as_string");
//			Object key = bucket.get("key");
//			// does it have a sub-aggregation in it?
//			boolean subAgg = false;
//			for(Map.Entry me : bucket.entrySet()) {
//				// which is a value that's a map and has buckets in 
//				Object v = me.getValue();
//				if (v instanceof Map && ((Map) v).containsKey("buckets")) {
//					Map vs = simplify((Map)v);
//					simpler.put(""+key, vs);
//					subAgg = true;
//					break; // that's all for this bucket
//				}
//			}
//			if ( ! subAgg) {
//				continue;
//			}
//		}
		// to {key -> sub-key -> stats
		
				
		return aggResult;
	}	
}
