/**
 * 
 */
package com.winterwell.es;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.xcontent.ToXContent;

import com.winterwell.gson.FlexiGson;
import com.winterwell.utils.StrUtils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.containers.Containers;

/**
 * Elastic Search utils
 * 
 * @author daniel
 *
 */
public class ESUtils {

	
	/**
	 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/1.x/
	 * mapping-core-types.html
	 * 
	 * @return an ElasticSearch type builder, which (being a Map) can be
	 *         submitted to PutMapping. This is just a convenience for <code>new ESType()</code>.
	 */
	public static ESType type() {
		return new ESType();
	}

	/**
	See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/query-dsl-query-string-query.html#_reserved_characters
		 */
	public static String escapeWord(String s) {
		if (s==null) return null;
		String chars = "+-&|!(){}[]^\"~*?:\\/";
		String s2 = StrUtils.escape(s, chars, '\\');
		// Note "&" will also have to be url-encoded!
		return s2;
	}
	
	/**
	 * HACK convert an ES class into a json object.
	 * TODO replace these ES classes with something nicer.
	 * Preferably auto-generated from the ES source
	 * @param qb
	 * @return
	 */
	public static Map jobj(ToXContent qb) {
		String s = qb.toString();
		return FlexiGson.fromJSON(s);
	}

	/**
	 * ElasticSearch gets upset by many keys (they explode the schema)
	 * so we can't store maps the obvious way.
	 * @param map
	 * @param k What to call the key-key in the output mini-maps, e.g. "key" or "k"
	 * @param v What to call the value-key in the output mini-maps, e.g. "value" or "v"
	 * @return a sort of association list, where each key:value mapping
	 * is turned into a {k:key, v:value} map in a list
	 */
	public static List<Map<String,Object>> assocListFromMap(Map<String, ?> map, String k, String v) {
		List<Map<String, Object>> list = Containers.apply(map.entrySet(), 
				e -> new ArrayMap(k, e.getKey(), v, e.getValue())
				);
		return list;
	}

	public static <V> Map<String, V> mapFromAssocList(List<Map<String,Object>> topmapList, 
			String k, String v) 
	{
		Map<String, V> map = new HashMap();
		for (Map<String, Object> kv : topmapList) {
			map.put((String)kv.get(k), (V) kv.get(v));
		}
		return map;
	}

}

