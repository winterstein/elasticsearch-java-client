/**
 * 
 */
package com.winterwell.es.client;

import java.io.Closeable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;

import com.winterwell.es.ESUtils;
import com.winterwell.es.client.admin.CreateIndexRequest;
import com.winterwell.es.client.admin.IndicesAliasesRequest;
import com.winterwell.utils.web.SimpleJson;

import com.winterwell.utils.FailureException;
import com.winterwell.utils.TodoException;

import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.threads.IProgress;
import com.winterwell.utils.time.Dt;
import com.winterwell.utils.time.TUnit;

/**
 * Reindex -- ie copy data from one index to another.
 * 
 * See 
 * https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html
 * 
 * This will scroll over all the data! There is no other way.
 * 
 * @testedby {@link ReindexRequestTest}
 * @author daniel
 *
 */
public class ReindexRequest extends ESHttpRequest<ReindexRequest, IESResponse>
//implements Callable, IProgress, Closeable 
{
	
	@Override
	public String toString() {
		return "ReindexRequest [" + body() + "]";
	}

	/**
	 * Copy all documents from one index into another.
	 * @param hClient
	 * @param fromIndex
	 * @param toIndex
	 */
	public ReindexRequest(ESHttpClient hClient, String fromIndex, String toIndex) {
		super(hClient, "_reindex");
		method = "POST";
		body().put("source", new ArrayMap("index", fromIndex));
		body().put("dest", new ArrayMap("index", toIndex));
		if (fromIndex.equals(toIndex)) {
			throw new TodoException();
		}
	}
	
	public ReindexRequest setQuery(QueryBuilder qb) {
		return setQuery(ESUtils.jobj(qb));
	}

	/**
	 * Filter the docs to be copied from source
	 * @param queryJson
	 * @return
	 */
	public ReindexRequest setQuery(Map queryJson) {
		Map source = (Map) body().get("source");
		source.put("query", queryJson);
		return this;
	}
	
	
}
