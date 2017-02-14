package com.winterwell.es;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.winterwell.utils.containers.ArrayMap;

/**
 * Helper for making ElasticSearch properties mappings. An ESType is just a map, 
 * and can be used with methods which take maps, such as
 * PutMappingRequestBuilder#setSource(). It defines handy builder methods
 * for setting up ElasticSearch settings.
 * 
 * Ref: http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-core-types.html
 * @author daniel
 *
 */
public class ESType extends LinkedHashMap<String,Object> {	
	private static final long serialVersionUID = 1L;

	public ESType string() {
		put("type", "string");
		return this;
	}
	
	public ESType date() {
		put("type", "date");
		return this;
	}
	
	public ESType object() {
		put("type", "object");
		return this;
	}

	/**
	 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/analysis-analyzers.html
	 * @param e.g. "keyword" or "standard"
	 */
	public ESType analyzer(String analyzer) {
		put("analyzer", analyzer);
		return this;
	}

	/**
	 * Inclusion in the _all field can be controlled on a field-by-field basis using the include_in_all setting, which defaults to true. 
	 * Setting include_in_all on an object (or on the root object) changes the default for all fields within that object.
	 * See: http://www.elasticsearch.org/guide/en/elasticsearch/guide/current/root-object.html
	 * @param included
	 */
	public ESType include_in_all(boolean included) {
		put("include_in_all", included);
		return this;
	}

	/**
	 * true: detect and index new fields (the default), false: ignore new fields, strict: throw an exception for new fields.
	 * Can be set on the root object and/or sub-objects. The setting cascades.
	 * See: http://www.elasticsearch.org/guide/en/elasticsearch/guide/current/dynamic-mapping.html
	 * @param dynamic true|false|strict
	 */
	public ESType dynamic(String dynamic) {
		put("dynamic", dynamic);
		return this;
	}
	/**
	 * Mark this property as not analyzed, i.e. the input strings will be indexed as-is
	 * without being tokenised. E.g. useful for id values.
	 * @return
	 */
	public ESType noAnalyzer() {
		put("index", "not_analyzed");
		return this;
	}
	
	/**
	 * Store but do not index this property (so you can't search on it).
	 */
	public ESType noIndex() {
		put("index", "no");
		return this;
	}

	public ESType() {
	}
	
	// NB: all-caps is a bit ugly, but we can't call this "long" or "Long", and "lng" is uglier still.
	public ESType LONG() {
		put("type", "long");
		return this;
	}	
	public ESType DOUBLE() {
		put("type", "double");
		return this;
	}
	public ESType INTEGER() {
		put("type", "integer");
		return this;
	}
	/**
	 * Set the nested properties for an object type.
	 *  Must only be called once.
	 * @param keyESType 
	 */
	public ESType properties(Map<String,ESType> props) {
		assert get("type")==null || get("type").equals("object") : this;	
		assert ! containsKey("properties") : this;
		put("properties", props);
		return this;
	}
	
//	/**
//	 * Set the nested properties for an object type. 
//	 * Must only be called once.
//	 * @param keyESType 
//	 */
//	public ESType properties(String propName, ESType propType, Object... otherNameTypePairs) {
//		assert get("type")==null || get("type").equals("object") : this;
//		assert ! containsKey("properties") : this;
//		ArrayMap props = new ArrayMap(propName, propType);
//		for(int i=0; i<otherNameTypePairs.length; i+=2) {
//			props.put((String)otherNameTypePairs[i], (Map)otherNameTypePairs[i+1]);
//		}
//		put("properties", props);
//		return this;
//	}
	
	/**
	 * Set a nested property for an object type. Can be called repeatedly
	 * to setup several properties.
	 * @param keyESType 
	 */
	public ESType property(String propertyName, ESType propertyType) {
		assert get("type")==null || get("type").equals("object") : this;	
		Map props = (Map) get("properties");
		if (props==null) {
			props = new ArrayMap();
			put("properties", props);
		}
		props.put(propertyName, propertyType);		
		return this;
	}
	/**
	 * type: boolean
	 */
	public ESType bool() {
		put("type", "boolean");
		return this;
	}
	/**
	 * Store this field in the index (as well as in the source, so it can later be retrieved using selective loading when searching).
	 */
	public ESType store(boolean store) {
		put("store", store);
		return this;
	}
}
