package com.winterwell.es.client.suggest;

import com.winterwell.es.ESType;
import com.winterwell.utils.containers.ArrayMap;

/**
 * 
 * Autocomplete. To use this, create a field of type completion - see {@link ESType#completion()}
 * @author daniel
 *
 */
public class Suggesters {

	public static Suggester autocomplete(String field, String prefix) {
		Suggester s = new Suggester("autocomplete");
		s.map.put("prefix", prefix);
		s.map.put("completion", new ArrayMap("field", field));
		return s;
	}

}
