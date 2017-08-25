package com.winterwell.es;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.log.Log;

/**
 * Helper for making ElasticSearch properties mappings. An ESType is just a map, 
 * and can be used with methods which take maps, such as
 * PutMappingRequestBuilder#setSource(). It defines handy builder methods
 * for setting up ElasticSearch settings.
 * 
 * E.g.
 * <code>
 * new ESType()
 * 	.property("my_id", ESType.keyword)
 *  .property("name", new ESType().text())
 * </code>
 *
 * Ref: http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-core-types.html
 * @author daniel
 *
 */
public class ESType extends LinkedHashMap<String,Object> {	
	private static final long serialVersionUID = 1L;
	
	public static final ESType keyword = new ESType().keyword();

	private transient boolean lock;
	
	public ESType copy() {
		// deep copy
		ESType copy = Utils.copy(this);
		return copy;
	}
	
	/**
	 * Use to switch off norms for efficiency. 
	 * This is only valid for certain types! So ESType will ignore it where invalid.
	 * https://www.elastic.co/guide/en/elasticsearch/reference/current/norms.html
	 * @param onOff
	 * @return this
	 */
	public ESType norms(boolean onOff) {
		if (NO_NORMS.contains(get("type"))) {
			Log.w("ESType", "Skip: norms are not valid for type "+get("type")+" in "+this);
			return this;
		}
		put("norms", onOff);
		return this;
	}
	
	static final List<String> NO_NORMS = Arrays.asList("date", "float", "double", "long", "float", "integer");
	
	/**
	 * Often you'll want text indexing for keyword search, but exact keyword indexing for e.g. alphabetical sorting.
	 * For this, a property can have several fields (aka "multi-fields").
	 *  
	 * See https://www.elastic.co/guide/en/elasticsearch/reference/current/multi-fields.html
	 * @param name the field name, e.g. "raw". You then access this in queries as property-name.field-name, e.g. "title.raw"
	 * @param type e.g. "keyword
	 * @return this
	 */
	public ESType field(String name, String type) {
		ESType f = new ESType();
		f.put("type", type);
		return field(name, f);
	}
	
	/**
	 * Often you'll want text indexing for keyword search, but exact keyword indexing for e.g. alphabetical sorting.
	 * For this, a property can have several fields (aka "multi-fields").
	 *  
	 * See https://www.elastic.co/guide/en/elasticsearch/reference/current/multi-fields.html
	 * @param name the field name, e.g. "raw". You then access this in queries as property-name.field-name, e.g. "title.raw"
	 * @param field e.g. new ESType().keyword()
	 * @return
	 */
	public ESType field(String name, ESType field) {
		Map fields = (Map) get("fields");
		if (fields==null) {
			fields = new ArrayMap();
			put("fields", fields);
		}
		fields.put(name, field);
		return this;
	}
	
	/**
	 * Analysed "body" text
	 * @return this
	 */
	public ESType text() {
		put("type", "text");
		return this;
	}
	
	public ESType keyword() {
		put("type", "keyword");
		return this;
	}
	
	@Override
	public Object put(String key, Object value) {
		assert ! lock : "Lifecycle bug: This object has been used already - dont modify it";
		return super.put(key, value);
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
	 * 
	 * Note: #no
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
		propertyType.lock = true;
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

	/**
	 * Set this for text fields to enable searches which sort on this field.
	 * See https://www.elastic.co/guide/en/elasticsearch/reference/current/fielddata.html
	 * 
	 * Before using this -- consider using {@link #field(String, String)} instead.
	 * 
	 * @param yes
	 */
	public ESType fielddata(boolean yes) {
		put("fielddata", yes);
		return this;
	}

	public ESType setParentType(String parentType) {
		assert ! Utils.isBlank(parentType);
		put("_parent", new ArrayMap("type", parentType));
		put("_routing", new ArrayMap("required", true));
		return this;
	}

	/**
	 * see https://www.elastic.co/guide/en/elasticsearch/reference/current/geo-point.html
	 * @return
	 */
	public ESType geo_point() {
		put("type", "geo_point");
		return this;
	}

	
}
