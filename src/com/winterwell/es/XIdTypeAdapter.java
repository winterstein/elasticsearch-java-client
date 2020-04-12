package com.winterwell.es;

import java.lang.reflect.Type;

import com.winterwell.gson.JsonDeserializationContext;
import com.winterwell.gson.JsonDeserializer;
import com.winterwell.gson.JsonElement;
import com.winterwell.gson.JsonObject;
import com.winterwell.gson.JsonParseException;
import com.winterwell.gson.JsonPrimitive;
import com.winterwell.gson.JsonSerializationContext;
import com.winterwell.gson.JsonSerializer;
import com.winterwell.web.data.XId;

/**
 * XId <> String
 * @author daniel
 *
 */
public final class XIdTypeAdapter implements JsonSerializer<XId>, JsonDeserializer<XId> {
	
	@Override
	public XId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		if (json.isJsonPrimitive()) { 
			return new XId(json.getAsString(), false);
		}
		// badly configured java->json
		JsonObject jobj = json.getAsJsonObject();
		String name = jobj.get("name").getAsString();
		String service = jobj.get("service").getAsString();
		return new XId(name, service, false);
	}

	@Override
	public JsonElement serialize(XId src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.toString());
	}
}
