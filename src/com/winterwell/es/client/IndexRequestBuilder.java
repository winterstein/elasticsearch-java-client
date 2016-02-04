package com.winterwell.es.client;


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

}
