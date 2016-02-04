package com.winterwell.es.client;


/**
 * @see org.elasticsearch.client.IndicesAdminClient
 * @author daniel
 *
 */
public class IndicesAdminClient {

	ESHttpClient hClient;


    /**
     * Deletes an index based on the index name.
     *
     * @param indices The indices to delete. Empty array to delete all indices.
     */
    public DeleteIndexRequest prepareDelete(String... indices) {
    	return new DeleteIndexRequest(hClient, indices);
    }
    
	public IndicesAdminClient(ESHttpClient esHttpClient) {
		hClient = esHttpClient;
	}

	public PutMappingRequestBuilder preparePutMapping(String idx) {
		return new PutMappingRequestBuilder(hClient, idx);
	}
	
	/**
     * Deletes mapping definition for a type into one or more indices.
     */
    public DeleteMappingRequestBuilder prepareDeleteMapping(String... indices) {
		return new DeleteMappingRequestBuilder(hClient, indices);
	}


    /**
     * Allows to add/remove aliases from indices.
     */
    public IndicesAliasesRequest prepareAliases() {
    	return new IndicesAliasesRequest(this); 
    }
    
	/**
	 * Convenience for calling {@link IndicesExistsRequestBuilder}
	 * @param index
	 * @return true if the index exists
	 */
	public boolean indexExists(String index) {
		IESResponse ok = new IndicesExistsRequestBuilder(this).setIndex(index).get();
		return ok.isSuccess();
	}

    /**
     * Creates an index using an explicit request allowing to specify the settings of the index.
     *
     * @param index The index name to create
     */
    public CreateIndexRequest prepareCreate(String index) {
    	return new CreateIndexRequest(hClient, index);
    }
    

}
