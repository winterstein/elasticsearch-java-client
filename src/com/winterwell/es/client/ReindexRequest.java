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

import com.winterwell.es.client.admin.CreateIndexRequest;
import com.winterwell.es.client.admin.IndicesAliasesRequest;
import com.winterwell.utils.web.SimpleJson;

import com.winterwell.utils.FailureException;
import com.winterwell.utils.TodoException;

import com.winterwell.utils.Utils;
import com.winterwell.utils.threads.IProgress;
import com.winterwell.utils.time.Dt;
import com.winterwell.utils.time.TUnit;

/**
 * Reindex -- ie copy data from one index to another.
 * 
 * See http://www.elasticsearch.org/guide/en/elasticsearch/guide/current/reindex.html
 * and http://www.elasticsearch.org/blog/changing-mapping-with-zero-downtime/
 * 
 * This will scroll over all the data! There is no other way.
 * 
 * @testedby {@link ReindexRequestTest}
 * @author daniel
 *
 */
public class ReindexRequest implements Callable, IProgress, Closeable {

	private String toIndex;
	private String fromIndex;
	private ESHttpClient client;
	

	@Override
	public String toString() {
		return "ReindexRequest [" + fromIndex + " to "
				+ toIndex + ", alias=" + alias + "]";
	}

	/**
	 * Copy all documents from one index into another.
	 * @param hClient
	 * @param fromIndex
	 * @param toIndex
	 */
	public ReindexRequest(ESHttpClient hClient, String fromIndex, String toIndex) {
		this.client = hClient;
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
		if (fromIndex.equals(toIndex)) {
			throw new TodoException();
		}
	}
	
	private String alias;
	private volatile String scrollId;
	
	/**
	 * If set, this request will finish by flipping the alias.
	 * @param alias The public alias, e.g. my_index and not my_index_v1
	 */
	public ReindexRequest setAlias(String alias) {
		this.alias = alias;
		if (alias.equals(fromIndex) || alias.equals(toIndex)) {
			throw new TodoException();
		}
		return this;
	}

	boolean keepRouting = true;
	boolean checkForFresh = false;
	
	/**
	 * TODO If true, then check for any new documents which may have come in whilst
	 * we were re-indexing.
	 * ??This needs a timestamp -- _timestamp is switched off by default in ES
	 * @param checkForFresh
	 */
	public void setCheckForFresh(boolean checkForFresh) {
		this.checkForFresh = checkForFresh;
		throw new TodoException();
	}	
	
	@Override
	public Object call() throws Exception {
		if ( ! client.getIndicesAdminClient().indexExists(toIndex)) {
			CreateIndexRequest cir = new CreateIndexRequest(client, toIndex);
			IESResponse ok = cir.get();
			if ( ! ok.isSuccess()) throw ok.getError();
		}
		try {				
			// Loop to catch updates
			while(true) {
				// Reindex!
				int batch = call2_reindex();
				if ( ! checkForFresh) break;
				// nothing in this batch -- stop
				if (batch==0) break;
			}
			
			if (alias!=null) {
				IndicesAliasesRequest iar = new IndicesAliasesRequest(client.getIndicesAdminClient());
				iar.removeAlias(fromIndex, alias);
				iar.addAlias(toIndex, alias);
				IESResponse ia = iar.get();
				if ( ! ia.isSuccess()) throw ia.getError();
			}
			return cnt;
		} finally {			
			close();
		}
	}
	
	private int call2_reindex() throws Exception {
		int batchCnt = 0;
		// set up a scan and scroll
		SearchRequestBuilder srb = new SearchRequestBuilder(client);
		srb.setIndex(fromIndex);
		srb.setFields("_routing","_source");
//		srb.setSearchType(SearchType.SCAN); see https://www.elastic.co/guide/en/elasticsearch/reference/5.1/breaking_50_search_changes.html#_literal_search_type_scan_literal_removed
		srb.setSort("_doc");
		srb.setScroll(new Dt(10, TUnit.MINUTE));
		SearchResponse sr = srb.get();
		scrollId = sr.getScrollId();
		List<Map> hits = sr.getHits();

		while(true) {
			SearchScrollRequestBuilder scroller = client.prepareSearchScroll(scrollId);
			scroller.setFields("_routing","_source");
			scroller.setScroll(new Dt(10, TUnit.MINUTE));
			sr = scroller.get();			
			if ( ! sr.isSuccess()) {
				throw sr.getError();
			}
//			badCnt += fails.length;			
			scrollId = sr.getScrollId();

			hits = sr.getHits();
			if (hits.isEmpty()) {
				break;
			}
			
			// copy into the toIndex
			BulkRequestBuilder bulk = client.prepareBulk();
			for (Map doc : hits) {
				IndexRequestBuilder index = new IndexRequestBuilder(client);
				index.setIndex(toIndex);
				Map docSrc = (Map) doc.get("_source");
				index.setBodyMap(docSrc);
				String type = (String) doc.get("_type");
				index.setType(type);
				// Preserve routing info?
				if (keepRouting) {
					String routing = (String) SimpleJson.get(doc, "fields", "_routing");
					if (routing!=null) index.setRouting(routing);
				}
				
				bulk.add(index);
			}			
			BulkResponse br = bulk.get();
			if ( ! br.isSuccess()) {
				throw br.getError();
			}
			cnt += hits.size();
			batchCnt+= hits.size();
		}
		return batchCnt;
	}

	@Override
	public void close() {
		if (scrollId==null) return;
		try {
			ClearScrollRequestBuilder cs = client.prepareClearScroll();
			cs.setScrollIds(Arrays.asList(scrollId));
			cs.execute();
			// NB: this request could fail!
			scrollId = null;
		} catch(Exception ex) {
			// oh well;
		}		
	}

	int cnt;
	
	/**
	 * @return [count-of-documents-reindexed, -1]
	 */
	@Override
	public double[] getProgress() {
		return new double[]{cnt, -1};
	}
	
	
	
}
