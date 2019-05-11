package com.winterwell.es.client;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.winterwell.utils.containers.AbstractIterator;
import com.winterwell.utils.time.Dt;

public class SearchRequestScrollOverAll implements Iterable<List<Map>> {

	private final ESHttpClient client;
	private final SearchRequestBuilder request;
	private int size;

	public SearchRequestScrollOverAll(ESHttpClient client, SearchRequestBuilder request, Dt keepAliveDuration) {
		this.client = client;
		request.setScroll(keepAliveDuration);
		this.request = request;
	}

	public Iterator<List<Map>> iterator() {
		Integer batchSize = request.getSize();
		if (batchSize==null) {
			// unset - use a chunky value
			int chunkyBatch = Math.min(1000, size);
			request.setSize(chunkyBatch);
		} else if (size > 0 && batchSize > size) {
			// reduce the batch size to fit small sizes
			request.setSize(size);
		}
		
		return new SRSOAIterator();	
	}
	
	class SRSOAIterator extends AbstractIterator<List<Map>> {
		private List<Map> hits;
		private SearchResponse response = request.get();
		int total;
		
		@Override
		protected List<Map> next2() {
			if (size>0 && total>=size) {
				return null; // done
			}
			if (hits != null) {
				String scrollId = response.getScrollId();
				response = client.prepareSearchScroll(scrollId).get();
			}

			hits = response.getHits();
			if (hits.isEmpty()) return null;
			// too many?
			total += hits.size();
			if (size>0 && total>size) {
				int over = total - size;
				assert over < hits.size();
				List<Map> lessHits = hits.subList(0, hits.size() - over);
				return lessHits;
			}				
			return hits;
		}
	};

	/**
	 * Sets the maximum records to return. If unset, you can fetch everything.
	 * 
	 * NB: The size of batches fetched from ES is set by setSize() on the SearchRequestBuilder. 
	 * @param n
	 */
	public void setSize(int n) {
		size = n;
	}

}
