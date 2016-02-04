package com.winterwell.es.client;

import java.util.Map;

public interface GetResponse extends IESResponse {

	/**
	 * @return the _source object from a Get request.
	 * NB: This is aware of the just-the-source GetRequestBuilder option 
	 */
	Map<String, Object> getSourceAsMap();
	
	/**
	 * @return the _source object from a Get request.
	 * NB: This is aware of the just-the-source GetRequestBuilder option 
	 */
	String getSourceAsString();
}
