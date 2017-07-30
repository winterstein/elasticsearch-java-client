package com.winterwell.es.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.util.ajax.JSON;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import com.winterwell.utils.StrUtils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.time.Dt;
import com.winterwell.utils.time.TUnit;

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
