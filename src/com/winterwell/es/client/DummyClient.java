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
import org.elasticsearch.action.count.CountRequest;
import org.elasticsearch.action.count.CountRequestBuilder;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.exists.ExistsRequest;
import org.elasticsearch.action.exists.ExistsRequestBuilder;
import org.elasticsearch.action.exists.ExistsResponse;
import org.elasticsearch.action.explain.ExplainRequest;
import org.elasticsearch.action.explain.ExplainRequestBuilder;
import org.elasticsearch.action.explain.ExplainResponse;
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
import org.elasticsearch.action.indexedscripts.delete.DeleteIndexedScriptRequest;
import org.elasticsearch.action.indexedscripts.delete.DeleteIndexedScriptRequestBuilder;
import org.elasticsearch.action.indexedscripts.delete.DeleteIndexedScriptResponse;
import org.elasticsearch.action.indexedscripts.get.GetIndexedScriptRequest;
import org.elasticsearch.action.indexedscripts.get.GetIndexedScriptRequestBuilder;
import org.elasticsearch.action.indexedscripts.get.GetIndexedScriptResponse;
import org.elasticsearch.action.indexedscripts.put.PutIndexedScriptRequest;
import org.elasticsearch.action.indexedscripts.put.PutIndexedScriptRequestBuilder;
import org.elasticsearch.action.indexedscripts.put.PutIndexedScriptResponse;
import org.elasticsearch.action.percolate.MultiPercolateRequest;
import org.elasticsearch.action.percolate.MultiPercolateRequestBuilder;
import org.elasticsearch.action.percolate.MultiPercolateResponse;
import org.elasticsearch.action.percolate.PercolateRequest;
import org.elasticsearch.action.percolate.PercolateRequestBuilder;
import org.elasticsearch.action.percolate.PercolateResponse;
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
import org.elasticsearch.action.suggest.SuggestRequest;
import org.elasticsearch.action.suggest.SuggestRequestBuilder;
import org.elasticsearch.action.suggest.SuggestResponse;
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
import org.elasticsearch.client.support.Headers;
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
	@Override
	public ThreadPool threadPool() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.internal.InternalClient#settings()
	 */
	@Override
	public Settings settings() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#admin()
	 */
	@Override
	public AdminClient admin() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#execute(org.elasticsearch.action.Action, org.elasticsearch.action.ActionRequest)
	 */
	@Override
	public <Request extends ActionRequest, Response extends ActionResponse, RequestBuilder extends ActionRequestBuilder<Request, Response, RequestBuilder>> ActionFuture<Response> execute(
			Action<Request, Response, RequestBuilder> action, Request request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#execute(org.elasticsearch.action.Action, org.elasticsearch.action.ActionRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public <Request extends ActionRequest, Response extends ActionResponse, RequestBuilder extends ActionRequestBuilder<Request, Response, RequestBuilder>> void execute(
			Action<Request, Response, RequestBuilder> action, Request request,
			ActionListener<Response> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareExecute(org.elasticsearch.action.Action)
	 */
	@Override
	public <Request extends ActionRequest, Response extends ActionResponse, RequestBuilder extends ActionRequestBuilder<Request, Response, RequestBuilder>> RequestBuilder prepareExecute(
			Action<Request, Response, RequestBuilder> action) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#index(org.elasticsearch.action.index.IndexRequest)
	 */
	@Override
	public ActionFuture<IndexResponse> index(IndexRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#index(org.elasticsearch.action.index.IndexRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void index(IndexRequest request,
			ActionListener<IndexResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareIndex()
	 */
	@Override
	public IndexRequestBuilder prepareIndex() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#update(org.elasticsearch.action.update.UpdateRequest)
	 */
	@Override
	public ActionFuture<UpdateResponse> update(UpdateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#update(org.elasticsearch.action.update.UpdateRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void update(UpdateRequest request,
			ActionListener<UpdateResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareUpdate()
	 */
	@Override
	public UpdateRequestBuilder prepareUpdate() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareUpdate(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public UpdateRequestBuilder prepareUpdate(String index, String type,
			String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareIndex(java.lang.String, java.lang.String)
	 */
	@Override
	public IndexRequestBuilder prepareIndex(String index, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareIndex(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public IndexRequestBuilder prepareIndex(String index, String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#delete(org.elasticsearch.action.delete.DeleteRequest)
	 */
	@Override
	public ActionFuture<DeleteResponse> delete(DeleteRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#delete(org.elasticsearch.action.delete.DeleteRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void delete(DeleteRequest request,
			ActionListener<DeleteResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#clearScroll(org.elasticsearch.action.search.ClearScrollRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void clearScroll(ClearScrollRequest request,
			ActionListener<ClearScrollResponse> listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public DeleteRequestBuilder prepareDelete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeleteRequestBuilder prepareDelete(String index, String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFuture<BulkResponse> bulk(BulkRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bulk(BulkRequest request, ActionListener<BulkResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BulkRequestBuilder prepareBulk() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFuture<GetResponse> get(GetRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void get(GetRequest request, ActionListener<GetResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GetRequestBuilder prepareGet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetRequestBuilder prepareGet(String index, String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFuture<MultiGetResponse> multiGet(MultiGetRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void multiGet(MultiGetRequest request, ActionListener<MultiGetResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MultiGetRequestBuilder prepareMultiGet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFuture<SearchResponse> search(SearchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void search(SearchRequest request, ActionListener<SearchResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SearchRequestBuilder prepareSearch(String... indices) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFuture<SearchResponse> searchScroll(SearchScrollRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void searchScroll(SearchScrollRequest request, ActionListener<SearchResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SearchScrollRequestBuilder prepareSearchScroll(String scrollId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFuture<MultiSearchResponse> multiSearch(MultiSearchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void multiSearch(MultiSearchRequest request, ActionListener<MultiSearchResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MultiSearchRequestBuilder prepareMultiSearch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFuture<TermVectorsResponse> termVectors(TermVectorsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void termVectors(TermVectorsRequest request, ActionListener<TermVectorsResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TermVectorsRequestBuilder prepareTermVectors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermVectorsRequestBuilder prepareTermVectors(String index, String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFuture<TermVectorsResponse> termVector(TermVectorsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void termVector(TermVectorsRequest request, ActionListener<TermVectorsResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TermVectorsRequestBuilder prepareTermVector() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TermVectorsRequestBuilder prepareTermVector(String index, String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFuture<MultiTermVectorsResponse> multiTermVectors(MultiTermVectorsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void multiTermVectors(MultiTermVectorsRequest request, ActionListener<MultiTermVectorsResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MultiTermVectorsRequestBuilder prepareMultiTermVectors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExplainRequestBuilder prepareExplain(String index, String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFuture<ExplainResponse> explain(ExplainRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void explain(ExplainRequest request, ActionListener<ExplainResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClearScrollRequestBuilder prepareClearScroll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFuture<ClearScrollResponse> clearScroll(ClearScrollRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FieldStatsRequestBuilder prepareFieldStats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFuture<FieldStatsResponse> fieldStats(FieldStatsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fieldStats(FieldStatsRequest request, ActionListener<FieldStatsResponse> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionFuture<CountResponse> count(CountRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void count(CountRequest arg0, ActionListener<CountResponse> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionFuture<DeleteIndexedScriptResponse> deleteIndexedScript(DeleteIndexedScriptRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteIndexedScript(DeleteIndexedScriptRequest arg0, ActionListener<DeleteIndexedScriptResponse> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionFuture<ExistsResponse> exists(ExistsRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exists(ExistsRequest arg0, ActionListener<ExistsResponse> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionFuture<GetIndexedScriptResponse> getIndexedScript(GetIndexedScriptRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getIndexedScript(GetIndexedScriptRequest arg0, ActionListener<GetIndexedScriptResponse> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Headers headers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFuture<MultiPercolateResponse> multiPercolate(MultiPercolateRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void multiPercolate(MultiPercolateRequest arg0, ActionListener<MultiPercolateResponse> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionFuture<PercolateResponse> percolate(PercolateRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void percolate(PercolateRequest arg0, ActionListener<PercolateResponse> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CountRequestBuilder prepareCount(String... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeleteIndexedScriptRequestBuilder prepareDeleteIndexedScript() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeleteIndexedScriptRequestBuilder prepareDeleteIndexedScript(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExistsRequestBuilder prepareExists(String... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetIndexedScriptRequestBuilder prepareGetIndexedScript() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetIndexedScriptRequestBuilder prepareGetIndexedScript(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiPercolateRequestBuilder prepareMultiPercolate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PercolateRequestBuilder preparePercolate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PutIndexedScriptRequestBuilder preparePutIndexedScript() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PutIndexedScriptRequestBuilder preparePutIndexedScript(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuggestRequestBuilder prepareSuggest(String... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFuture<PutIndexedScriptResponse> putIndexedScript(PutIndexedScriptRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putIndexedScript(PutIndexedScriptRequest arg0, ActionListener<PutIndexedScriptResponse> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionFuture<SuggestResponse> suggest(SuggestRequest arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void suggest(SuggestRequest arg0, ActionListener<SuggestResponse> arg1) {
		// TODO Auto-generated method stub
		
	}

}
