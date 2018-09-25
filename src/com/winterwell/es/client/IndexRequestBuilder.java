package com.winterwell.es.client;

import java.util.Arrays;

/**
 * Index (store) a document.
 */
public class IndexRequestBuilder extends ESHttpRequest<IndexRequestBuilder, IESResponse> {

	/**
	 * Which index? You must call {@link #setIndex(String)} or {@link #setIndices(String...)} before use!
	 * @param esHttpClient
	 */
	public IndexRequestBuilder(ESHttpClient esHttpClient) {
		super(esHttpClient, null);
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
	 * If true, this will fail if a document with this ID already exists.
	 * @param createOnly
	 */
	public void setOpTypeCreate(boolean createOnly) {
		if (createOnly) params.put("op_type", "create");
		else params.remove("op_type");
	}

}
