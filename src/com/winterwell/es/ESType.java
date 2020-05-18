package com.winterwell.es;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.winterwell.utils.Utils;
import com.winterwell.utils.Warning;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.log.Log;
import com.winterwell.utils.time.Time;

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
	
	/**
	 * Convenience for a no-options keyword
	 */
	public static final ESType keyword = new ESType().keyword().lock();

	private transient boolean lock;
	
	/**
	 * Once a type object is locked, the Java object cannot be modified 
	 * (this has no effect on the ES server). 
	 * This makes it safe to reuse and share ESType objects in your code.
	 * NB: You can copy() a locked object, then modify the copy.
	 * @return this
	 */
	public ESType lock() {
		lock = true;
		return this;
	}
	
	public ESType copy() {
		// deep copy
		ESType copy = Utils.copy(this);
		copy.lock = false; // make sure it is unlocked
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
		lockCheck();
		Map fields = (Map) get("fields");
		if (fields==null) {
			fields = new ArrayMap();
			put("fields", fields);
		}
		fields.put(name, field);
		return this;
	}
	
	private void lockCheck() {
		if (lock) throw new IllegalStateException("Lifecycle bug: Cannot modify locked type.");
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
	
	/**
	 * @deprecated You can use this key:value method - 
	 * But do you want {@link #property(String, ESType)} instead? 
	 */
	@Override
	public Object put(String key, Object value) {
		lockCheck();
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
		put("index", "false");
		return this;
	}
	
	/**
	 * Store but do not index this property (so you can't search on it).
	 * 
	 * This method handles index:no vs enabled:false.
	 * 
	 * So it should be called after type info has been set.
	 * <p>
	 * index vs enabled? What's the difference?
	 * 
	 *  - index is for primitive fields
	 *  - enabled is for Object fields.
	 *  
	 * <p>
	 * See
	 * https://stackoverflow.com/questions/20661980/what-is-the-difference-between-enabled-false-and-index-no-in-elasticsearch 
	 * https://discuss.elastic.co/t/whats-the-difference-between-enabled-index-and-store/10717/4 
	 * ref: https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-index.html
	 * ref: https://www.elastic.co/guide/en/elasticsearch/reference/current/enabled.html
	 * 
	 * 
	 */
	public ESType noIndex() {
//		 * NB: noIndex This seems to be broken! But enabled:false works?
//				 * It's not clear what versions of ES support what! Tested on ES 5.1
//		put("index", false); // FIXME this is breaking (seen Dec 17, ES 5.1)?!
		// object type?
		if (get("type")==null || get("type").equals("object")) {
			return enabled(false);
		}
		assert ! containsKey("properties") : this;
		
		// Hm - when did "no" become legacy??
		// https://www.elastic.co/guide/en/elasticsearch/reference/5.6/mapping-index.html
		
		return index(false);
	}

	public ESType() {
	}
	
	
	/**
	 * Pick a type from the Java class, e.g. Double -> double
	 * @param type
	 */
	public ESType(Class<?> type) {
		setType(type);
	}

	/**
	 * Convenience for setting one of the primitive types. 
	 * Best practice is to use methods like {@link #DOUBLE()} or {@link #keyword()} instead,
	 * unless you're doing reflection.
	 * @param klass
	 * @return this
	 */
	public ESType setType(Class klass) throws IllegalArgumentException {
		String type = typeForClass(klass);
		put("type", type);
		return this;
	}
	
	private String typeForClass(Class klass) {
		if (klass==Long.class || klass==long.class) {
			return "long";
		}
		if (klass==Double.class || klass==double.class) {
			return "double";
		}
		if (klass==Float.class || klass==float.class) {
			return "float";
		}
		if (klass==Integer.class || klass==int.class) {
			return "integer";
		}
		if (klass==Boolean.class || klass==boolean.class) {
			return "boolean";
		}
		if (klass==Date.class || klass==Time.class || klass == Calendar.class) {
			return "date";
		}
		if (klass==String.class) {
			Log.w("ESType", "String given type 'text' - but do you want keyword? Best practice is to set this type explicitly.");
			return "text";
		}
		throw new IllegalArgumentException("This method only handles some types. Unrecognised: "+klass);
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
		propertyType.lock();
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
	 * https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-store.html
	 * 
	 * Store this field in the index (as well as in the source, so it can later be retrieved using selective loading when searching).
	 */
	public ESType store(boolean store) {
		put("store", store);
		return this;
	}
	
	/**
	 * @see #noIndex()
	 * @ref https://www.elastic.co/guide/en/elasticsearch/reference/current/enabled.html
	 */
	public ESType enabled(boolean enabledForIndex) {
		if (get("type")!=null && ! get("type").equals("object")) {
			Log.w("ESType", new Warning("noIndex / enabled is only available for type:object. Not "+this));
			return this;
		}
		put("enabled", enabledForIndex);
		return this;
	}

	/**
	 * @see #noIndex() 
	 */
	public ESType index(boolean b) {
		if ("object".equals(get("type")) || containsKey("properties")) {
			Log.w("ESType", new Warning("index is only available for primitive types. Not "+this+". Use enabled() or noIndex()"));
			return this;
		}
		put("index", false);
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

	
	// gone in ESv7
//	public ESType setParentType(String parentType) {
//		assert ! Utils.isBlank(parentType);
//		put("_parent", new ArrayMap("type", parentType));
//		put("_routing", new ArrayMap("required", true));
//		return this;
//	}
	
	

	/**
	 * see https://www.elastic.co/guide/en/elasticsearch/reference/current/geo-point.html
	 * @return
	 */
	public ESType geo_point() {
		put("type", "geo_point");
		return this;
	}

	/**
	 * https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters.html#completion-suggester
	 * <p>
	 * Completion suggester is optimized for speed. The suggester uses data structures that enable fast lookups, 
	 * but are costly to build and are stored in-memory.
	 */
	public ESType completion() {
		put("type", "completion");
		return this;
	}

	/**
	 * @return true if this is indexed (the default), 
	 * false if index:false or enabled:false has been set.
	 */
	public boolean isIndexed() {
		boolean noi = Boolean.FALSE.equals(get("enabled")) 
				|| Boolean.FALSE.equals(get("index"));
		return ! noi;
	}


	
}
