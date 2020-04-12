package com.winterwell.es.client.suggest;

import com.winterwell.es.ESType;
import com.winterwell.utils.containers.ArrayMap;

/**
 * 
 * 
 * 
 * See https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters.html
 * @author daniel
 *
 */
public class Suggesters {

	/**
	 * Autocomplete. To use this, create a field of type completion - see {@link ESType#completion()}
	 * 
	 * https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters.html#completion-suggester
	 * @param field
	 * @param prefix
	 * @return
	 */
	public static Suggester autocomplete(String field, String prefix) {
		Suggester s = new Suggester("autocomplete");
		s.map.put("prefix", prefix);
		s.map.put("completion", new ArrayMap("field", field));
		return s;
	}

}
