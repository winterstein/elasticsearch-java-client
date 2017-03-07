package com.winterwell.es;

import java.lang.reflect.Type;
import java.util.concurrent.Callable;

import com.winterwell.gson.Gson;
import com.winterwell.gson.GsonBuilder;
import com.winterwell.gson.JsonDeserializationContext;
import com.winterwell.gson.JsonDeserializer;
import com.winterwell.gson.JsonElement;
import com.winterwell.gson.JsonParseException;
import com.winterwell.gson.JsonPrimitive;
import com.winterwell.gson.JsonSerializationContext;
import com.winterwell.gson.JsonSerializer;
import com.winterwell.gson.StandardAdapters;
import com.winterwell.utils.Dep;
import com.winterwell.utils.containers.Properties;

import com.winterwell.utils.time.Time;
import com.winterwell.utils.web.IHasJson;
import com.winterwell.web.data.XId;

/**
 * @deprecated Use {@link Dep}
 * @author daniel
 *
 */
class GsonSetup implements Callable<Gson> {
	
	public GsonSetup() {
	}
	
	public static GsonBuilder gb = new GsonBuilder();
	static {
		// "Standard" mappings
		gb.registerTypeAdapter(Time.class, new StandardAdapters.TimeTypeAdapter());
		gb.registerTypeAdapter(XId.class, new XIdTypeAdapter());
		gb.registerTypeAdapter(Class.class, new StandardAdapters.ClassTypeAdapter());

//		gb.registerTypeAdapter(Properties.class, new PropertiesTypeAdapter());
		
//		SharedStatic.putFactory(Gson.class, new GsonSetup());
	}
	
	public Gson call() {		
		return gb.create();
	} 
}

