package com.winterwell.es.client;

import java.util.Arrays;
import java.util.Map;

/**
 * Index (store) a document.
 */
public class IndexRequestBuilder extends ESHttpRequest<IndexRequestBuilder, IESResponse> {

	/**
	 * Which index? You must call {@link #setIndex(String)} or {@link #setIndices(String...)} before use!
	 * @param esHttpClient
	 */
	public IndexRequestBuilder(ESHttpClient esHttpClient) {
		super(esHttpClient);
		method = "POST";
		bulkOpName = "index";
	}
	
	/**
	 * Set the request body by converting a pojo to json. The request body can only be set once.
	 * Convenience for {@link #setBodyJson(String)} with a standard pojo->json convertor.
	 * @param doc This is the document you want to store!
	 * @return 
	 * @return this
	 * @see #setBodyJson(String)
	 */
	public IndexRequestBuilder setBodyDoc(Object doc) throws IllegalStateException {
		String json = gson().toJson(doc);
		setBodyJson(json);
		return this;
	}
	
	

	/**
	 * Force a refresh?
	 * See https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-refresh.html
	 * @param string false | true | wait_for
	 */
	public void setRefresh(String refresh) {
		assert Arrays.asList("false","true","wait_for").contains(refresh) : refresh;
		params.put("refresh", refresh);		
	}

	/**
	 * If true, this will fail if a document with this ID already exists.
	 * @param createOnly
	 */
	public void setOpTypeCreate(boolean createOnly) {
		if (createOnly) params.put("op_type", "create");
		else params.remove("op_type");
	}

}
