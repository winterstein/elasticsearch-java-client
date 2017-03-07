package com.winterwell.es;

import java.lang.reflect.Type;

import com.winterwell.gson.JsonDeserializationContext;
import com.winterwell.gson.JsonDeserializer;
import com.winterwell.gson.JsonElement;
import com.winterwell.gson.JsonParseException;
import com.winterwell.gson.JsonPrimitive;
import com.winterwell.gson.JsonSerializationContext;
import com.winterwell.gson.JsonSerializer;
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
