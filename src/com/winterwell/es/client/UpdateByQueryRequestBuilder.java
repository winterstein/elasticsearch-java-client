package com.winterwell.es.client;

import java.util.Map;

import com.winterwell.es.client.query.ESQueryBuilder;
import com.winterwell.es.client.query.ESQueryBuilders;
import com.winterwell.utils.ReflectionUtils;

public class UpdateByQueryRequestBuilder extends UpdateRequestBuilder {


	@Override
	protected void get2_safetyCheck() {
		if (indices==null || indices.size()==0) throw new IllegalStateException("No index specified for update: "+this);	
	}
	
	public UpdateByQueryRequestBuilder(ESHttpClient hClient) {
		super(hClient);
		ReflectionUtils.setPrivateField(this, "endpoint", "_update_by_query");
	}
	
	/**
	 * Convenience method for building up AND queries.
	 * This will set the query if null, or combine with bool-query *must* if not null.
	 * 
	 * @see #setQuery(ESQueryBuilder)
	 * 
	 * @param qb
	 * @return 
	 */
	public UpdateByQueryRequestBuilder addQuery(ESQueryBuilder qb) {
		Map query = (Map) body().get("query");
		if (query==null) {
			setQuery(qb);
			return this;
		}
		// Add to it
		// Is it a boolean?
//		String qtype = (String) Containers.first(query.keySet());
//		if (qtype != "bool") {
			ESQueryBuilder qand = ESQueryBuilders.must(query, qb);
			setQuery(qand.toJson2());
//		} else {
			// TODO merge!			
//		}
		return this;
	}
	

	/**
	 * Best practice: Use this to set the query. / filter.
	 * @param qb Cannot be modified afterwards.
	 * @return
	 */
	public UpdateByQueryRequestBuilder setQuery(ESQueryBuilder qb) {
		return setQuery(qb.toJson2());
	}

	public UpdateByQueryRequestBuilder setQuery(Map queryJson) {
		body().put("query", queryJson);
		return this;
	}

}
