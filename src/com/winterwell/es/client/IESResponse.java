package com.winterwell.es.client;

import java.util.List;
import java.util.Map;

public interface IESResponse {

	boolean isSuccess();

	boolean isAcknowledged();

	Exception getError();
	
	/**
	 * @param name
	 * @return often a single value List (eg from get or update requests)
	 */
	List getField(String name);	
	
    Map getFieldsFromGet();
    
	Map<String, Object> getParsedJson();
	
	String getJson();

	/**
	 * Convenience method: Check that the response was error-free -- and throw
	 * a runtime-exception if there was an error.
	 * @return this
	 */
	IESResponse check();

}