package com.winterwell.es.client.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.winterwell.es.client.ESHttpClient;
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
	 * @deprecated types are gone - use the other constructor
	 * Sets field.type info for a type (or several types)
	 * @param idx The index
	 * @param type 
	 */
	public PutMappingRequestBuilder preparePutMapping(String idx, String type) {
		return new PutMappingRequestBuilder(hClient, idx, type);
	}
	
	/**
	 * Sets field.type info for the index
	 * @param idx The index
	 */
	public PutMappingRequestBuilder preparePutMapping(String idx) {
		return new PutMappingRequestBuilder(hClient, idx, null);
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

	/**
	 * Convenience for using {@link GetAliasesRequest}
	 * @param indexOrAlias
	 * @return
	 */
	public List<String> getAliasesResponse(String indexOrAlias) {
		GetAliasesRequest req = getAliases(indexOrAlias);
		IESResponse resp = req.get();
		Map<String, Object> jobj = resp.getParsedJson();
		Set<String> indices = jobj.keySet();
		return new ArrayList(indices);
	}
    

}
