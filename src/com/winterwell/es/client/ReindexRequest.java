/**
 * 
 */
package com.winterwell.es.client;

import java.util.Map;

import org.elasticsearch.index.query.QueryBuilder;

import com.winterwell.es.ESUtils;
import com.winterwell.utils.TodoException;
import com.winterwell.utils.containers.ArrayMap;

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
		setIndex(null); // NB: set indices to [null] to avoid the _all default in getUrl()
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
