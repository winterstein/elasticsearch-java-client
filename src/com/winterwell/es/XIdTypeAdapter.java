package com.winterwell.es;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.winterwell.web.data.XId;

public final class XIdTypeAdapter implements JsonSerializer<XId>, JsonDeserializer<XId> {
	
	@Override
	public XId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		return new XId(json.getAsString(), false);
	}

	@Override
	public JsonElement serialize(XId src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.toJSONString());
	}
}
