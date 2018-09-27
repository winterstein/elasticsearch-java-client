/**
 * 
 */
package com.winterwell.es.client;

import java.util.Map;

import org.elasticsearch.action.Action;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.explain.ExplainRequest;
import org.elasticsearch.action.explain.ExplainRequestBuilder;
import org.elasticsearch.action.explain.ExplainResponse;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesRequest;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesRequestBuilder;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesResponse;
import org.elasticsearch.action.fieldstats.FieldStatsRequest;
import org.elasticsearch.action.fieldstats.FieldStatsRequestBuilder;
import org.elasticsearch.action.fieldstats.FieldStatsResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollRequestBuilder;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.action.termvectors.MultiTermVectorsRequest;
import org.elasticsearch.action.termvectors.MultiTermVectorsRequestBuilder;
import org.elasticsearch.action.termvectors.MultiTermVectorsResponse;
import org.elasticsearch.action.termvectors.TermVectorsRequest;
import org.elasticsearch.action.termvectors.TermVectorsRequestBuilder;
import org.elasticsearch.action.termvectors.TermVectorsResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.threadpool.ThreadPool;

/**
 * In order to use ES's builder classes, you sometimes need a Client.
 * @author daniel
 *
 */
public class DummyClient implements Client
//, InternalClient 
{

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.internal.InternalGenericClient#threadPool()
	 */
	
	public ThreadPool threadPool() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.internal.InternalClient#settings()
	 */
	
	public Settings settings() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#close()
	 */
	
	public void close() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#admin()
	 */
	
	public AdminClient admin() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#execute(org.elasticsearch.action.Action, org.elasticsearch.action.ActionRequest)
	 */
	
	public <Request extends ActionRequest, Response extends ActionResponse, RequestBuilder extends ActionRequestBuilder<Request, Response, RequestBuilder>> ActionFuture<Response> execute(
			Action<Request, Response, RequestBuilder> action, Request request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#execute(org.elasticsearch.action.Action, org.elasticsearch.action.ActionRequest, org.elasticsearch.action.ActionListener)
	 */
	
	public <Request extends ActionRequest, Response extends ActionResponse, RequestBuilder extends ActionRequestBuilder<Request, Response, RequestBuilder>> void execute(
			Action<Request, Response, RequestBuilder> action, Request request,
			ActionListener<Response> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareExecute(org.elasticsearch.action.Action)
	 */
	
	public <Request extends ActionRequest, Response extends ActionResponse, RequestBuilder extends ActionRequestBuilder<Request, Response, RequestBuilder>> RequestBuilder prepareExecute(
			Action<Request, Response, RequestBuilder> action) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#index(org.elasticsearch.action.index.IndexRequest)
	 */
	
	public ActionFuture<IndexResponse> index(IndexRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#index(org.elasticsearch.action.index.IndexRequest, org.elasticsearch.action.ActionListener)
	 */
	
	public void index(IndexRequest request,
			ActionListener<IndexResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareIndex()
	 */
	
	public IndexRequestBuilder prepareIndex() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#update(org.elasticsearch.action.update.UpdateRequest)
	 */
	
	public ActionFuture<UpdateResponse> update(UpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#update(org.elasticsearch.action.update.UpdateRequest, org.elasticsearch.action.ActionListener)
	 */
	
	public void update(UpdateRequest request,
			ActionListener<UpdateResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareUpdate()
	 */
	
	public UpdateRequestBuilder prepareUpdate() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareUpdate(java.lang.String, java.lang.String, java.lang.String)
	 */
	
	public UpdateRequestBuilder prepareUpdate(String index, String type,
			String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareIndex(java.lang.String, java.lang.String)
	 */
	
	public IndexRequestBuilder prepareIndex(String index, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareIndex(java.lang.String, java.lang.String, java.lang.String)
	 */
	
	public IndexRequestBuilder prepareIndex(String index, String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#delete(org.elasticsearch.action.delete.DeleteRequest)
	 */
	
	public ActionFuture<DeleteResponse> delete(DeleteRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#delete(org.elasticsearch.action.delete.DeleteRequest, org.elasticsearch.action.ActionListener)
	 */
	
	public void delete(DeleteRequest request,
			ActionListener<DeleteResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#clearScroll(org.elasticsearch.action.search.ClearScrollRequest, org.elasticsearch.action.ActionListener)
	 */
	
	public void clearScroll(ClearScrollRequest request,
			ActionListener<ClearScrollResponse> listener) {
		// TODO Auto-generated method stub

	}

	
	public DeleteRequestBuilder prepareDelete() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public DeleteRequestBuilder prepareDelete(String index, String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ActionFuture<BulkResponse> bulk(BulkRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void bulk(BulkRequest request, ActionListener<BulkResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	
	public BulkRequestBuilder prepareBulk() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ActionFuture<GetResponse> get(GetRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void get(GetRequest request, ActionListener<GetResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	
	public GetRequestBuilder prepareGet() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public GetRequestBuilder prepareGet(String index, String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ActionFuture<MultiGetResponse> multiGet(MultiGetRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void multiGet(MultiGetRequest request, ActionListener<MultiGetResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	
	public MultiGetRequestBuilder prepareMultiGet() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ActionFuture<SearchResponse> search(SearchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void search(SearchRequest request, ActionListener<SearchResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	
	public SearchRequestBuilder prepareSearch(String... indices) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ActionFuture<SearchResponse> searchScroll(SearchScrollRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void searchScroll(SearchScrollRequest request, ActionListener<SearchResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	
	public SearchScrollRequestBuilder prepareSearchScroll(String scrollId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ActionFuture<MultiSearchResponse> multiSearch(MultiSearchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void multiSearch(MultiSearchRequest request, ActionListener<MultiSearchResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	
	public MultiSearchRequestBuilder prepareMultiSearch() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ActionFuture<TermVectorsResponse> termVectors(TermVectorsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void termVectors(TermVectorsRequest request, ActionListener<TermVectorsResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	
	public TermVectorsRequestBuilder prepareTermVectors() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public TermVectorsRequestBuilder prepareTermVectors(String index, String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ActionFuture<TermVectorsResponse> termVector(TermVectorsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void termVector(TermVectorsRequest request, ActionListener<TermVectorsResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	
	public TermVectorsRequestBuilder prepareTermVector() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public TermVectorsRequestBuilder prepareTermVector(String index, String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ActionFuture<MultiTermVectorsResponse> multiTermVectors(MultiTermVectorsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void multiTermVectors(MultiTermVectorsRequest request, ActionListener<MultiTermVectorsResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	
	public MultiTermVectorsRequestBuilder prepareMultiTermVectors() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ExplainRequestBuilder prepareExplain(String index, String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ActionFuture<ExplainResponse> explain(ExplainRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void explain(ExplainRequest request, ActionListener<ExplainResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	
	public ClearScrollRequestBuilder prepareClearScroll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ActionFuture<ClearScrollResponse> clearScroll(ClearScrollRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public FieldStatsRequestBuilder prepareFieldStats() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ActionFuture<FieldStatsResponse> fieldStats(FieldStatsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void fieldStats(FieldStatsRequest request, ActionListener<FieldStatsResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	
	public Client filterWithHeader(Map<String, String> headers) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ActionFuture<FieldCapabilitiesResponse> fieldCaps(FieldCapabilitiesRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void fieldCaps(FieldCapabilitiesRequest arg0, ActionListener<FieldCapabilitiesResponse> arg1) {
		// TODO Auto-generated method stub
		
	}

	
	public FieldCapabilitiesRequestBuilder prepareFieldCaps() {
		// TODO Auto-generated method stub
		return null;
	}

}
