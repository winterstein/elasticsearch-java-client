package com.winterwell.es.client.admin;

import org.elasticsearch.index.IndexSettings;

import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.es.client.IESResponse;


/**
 * @see org.elasticsearch.client.IndicesAdminClient
 * @author daniel
 *
 */
public class IndicesAdminClient {

	ESHttpClient hClient;

	public StatsRequest listIndices() {
		// a light-weight StatsRequest
		return new StatsRequest(hClient).setType("store");
		// /_stats/store,docs,indexing
	}

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

	/**
	 * Sets field.type info for a type (or several types)
	 * @param idx The index
	 * @param type 
	 * @return
	 */
	public PutMappingRequestBuilder preparePutMapping(String idx, String type) {
		return new PutMappingRequestBuilder(hClient, idx, type);
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

	public IndexSettingsRequest indexSettings(String alias) {
		return new IndexSettingsRequest(hClient).setIndex(alias);
	}

	public GetAliasesRequest getAliases(String indexOrAlias) {
		return new GetAliasesRequest(hClient).setIndex(indexOrAlias);
	}
    

}
