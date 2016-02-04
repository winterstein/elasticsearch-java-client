/**
 * 
 */
package com.winterwell.es;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.winterwell.es.client.ESHttpClient;
import com.winterwell.utils.StrUtils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.containers.SharedStatic;
import com.winterwell.utils.time.Time;
import com.winterwell.utils.web.IHasJson;

import creole.data.XId;

/**
 * Elastic Search utils
 * 
 * @author daniel
 *
 */
public class ESUtils {

	public static Gson gson() {
		// From
		// https://sites.google.com/site/gson/gson-user-guide#TOC-Gson-Performance-and-Scalability
		// The Gson instance does not maintain any state while invoking Json
		// operations. So, you are free to reuse the same object for multiple
		// Json serialization and deserialization operations.
		return SharedStatic.get(Gson.class, new GsonSetup());
	}

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

}

class TimeTypeAdapter implements JsonSerializer<Time>, JsonDeserializer<Time> {
	@Override
	public JsonElement serialize(Time src, Type srcType,
			JsonSerializationContext context) {
		return new JsonPrimitive(src.getTime());
	}

	@Override
	public Time deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		return new Time(json.getAsLong());
	}
}

class XIdTypeAdapter implements JsonSerializer<XId>, JsonDeserializer<XId> {
	@Override
	public JsonElement serialize(XId src, Type srcType,
			JsonSerializationContext context) {
		return new JsonPrimitive(src.toString());
	}

	@Override
	public XId deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		return new XId(json.getAsString(), false);
	}
}

class ClassTypeAdapter implements JsonSerializer<Class>,
		JsonDeserializer<Class> {
	@Override
	public JsonElement serialize(Class src, Type srcType,
			JsonSerializationContext context) {
		return new JsonPrimitive(src.getCanonicalName());
	}

	@Override
	public Class deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		try {
			return Class.forName(json.getAsString());
		} catch (ClassNotFoundException e) {
			throw new JsonParseException(e);
		}
	}
}



