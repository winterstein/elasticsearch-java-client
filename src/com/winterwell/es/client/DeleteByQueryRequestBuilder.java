package com.winterwell.es.client;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * @see org.elasticsearch.action.deletebyquery.DeleteByQueryRequestBuilder
 * @author daniel
 *
 */
public class DeleteByQueryRequestBuilder extends ESHttpRequest<DeleteByQueryRequestBuilder,IESResponse> {


	public DeleteByQueryRequestBuilder(ESHttpClient hClient, String index) {
		super(hClient, "_query");
		method = "DELETE";
		setIndex(index);
	}
	
    /**
     * The document types to execute the search against. Defaults to be executed against
     * all types.
     */
	public DeleteByQueryRequestBuilder setTypes(String... types) {
		assert types.length==1 : "TODO";
		setType(types[0]);
		return this;
	}


	public DeleteByQueryRequestBuilder setQuery(QueryBuilder qb) {
		body.put("query", qb.toString());
		return this;
	}	


	public DeleteByQueryRequestBuilder setFrom(int i) {
		params.put("from", i);
		return this;
	}
	
}
