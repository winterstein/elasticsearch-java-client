package com.winterwell.es;

import java.lang.reflect.Type;
import java.util.concurrent.Callable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import winterwell.utils.containers.Properties;

import com.winterwell.utils.time.Time;
import com.winterwell.utils.web.IHasJson;

import creole.data.XId;

class GsonSetup implements Callable<Gson> {
	
	public GsonSetup() {
	}
	
	public static GsonBuilder gb = new GsonBuilder();
	static {
		// "Standard" mappings
		gb.registerTypeAdapter(Time.class, new TimeTypeAdapter());
		gb.registerTypeAdapter(XId.class, new XIdTypeAdapter());
		gb.registerTypeAdapter(Class.class, new ClassTypeAdapter());

//		gb.registerTypeAdapter(Properties.class, new PropertiesTypeAdapter());
		
//		SharedStatic.putFactory(Gson.class, new GsonSetup());
	}
	
	public Gson call() {		
		return gb.create();
	} 
}

