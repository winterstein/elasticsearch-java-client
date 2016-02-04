/**
 * 
 */
package com.winterwell.es.client;

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
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequest;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequestBuilder;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.explain.ExplainRequest;
import org.elasticsearch.action.explain.ExplainRequestBuilder;
import org.elasticsearch.action.explain.ExplainResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.mlt.MoreLikeThisRequest;
import org.elasticsearch.action.mlt.MoreLikeThisRequestBuilder;
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
import org.elasticsearch.action.termvector.MultiTermVectorsRequest;
import org.elasticsearch.action.termvector.MultiTermVectorsRequestBuilder;
import org.elasticsearch.action.termvector.MultiTermVectorsResponse;
import org.elasticsearch.action.termvector.TermVectorRequest;
import org.elasticsearch.action.termvector.TermVectorRequestBuilder;
import org.elasticsearch.action.termvector.TermVectorResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.internal.InternalClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.threadpool.ThreadPool;

/**
 * In order to use ES's builder classes, you sometimes need a Client.
 * @author daniel
 *
 */
public class DummyClient implements Client, InternalClient {

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
	 * @see org.elasticsearch.client.Client#prepareDelete()
	 */
	@Override
	public DeleteRequestBuilder prepareDelete() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareDelete(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DeleteRequestBuilder prepareDelete(String index, String type,
			String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#bulk(org.elasticsearch.action.bulk.BulkRequest)
	 */
	@Override
	public ActionFuture<BulkResponse> bulk(BulkRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#bulk(org.elasticsearch.action.bulk.BulkRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void bulk(BulkRequest request, ActionListener<BulkResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareBulk()
	 */
	@Override
	public BulkRequestBuilder prepareBulk() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#deleteByQuery(org.elasticsearch.action.deletebyquery.DeleteByQueryRequest)
	 */
	@Override
	public ActionFuture<DeleteByQueryResponse> deleteByQuery(
			DeleteByQueryRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#deleteByQuery(org.elasticsearch.action.deletebyquery.DeleteByQueryRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void deleteByQuery(DeleteByQueryRequest request,
			ActionListener<DeleteByQueryResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareDeleteByQuery(java.lang.String[])
	 */
	@Override
	public DeleteByQueryRequestBuilder prepareDeleteByQuery(String... indices) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#get(org.elasticsearch.action.get.GetRequest)
	 */
	@Override
	public ActionFuture<GetResponse> get(GetRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#get(org.elasticsearch.action.get.GetRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void get(GetRequest request, ActionListener<GetResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareGet()
	 */
	@Override
	public GetRequestBuilder prepareGet() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareGet(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public GetRequestBuilder prepareGet(String index, String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#multiGet(org.elasticsearch.action.get.MultiGetRequest)
	 */
	@Override
	public ActionFuture<MultiGetResponse> multiGet(MultiGetRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#multiGet(org.elasticsearch.action.get.MultiGetRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void multiGet(MultiGetRequest request,
			ActionListener<MultiGetResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareMultiGet()
	 */
	@Override
	public MultiGetRequestBuilder prepareMultiGet() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#count(org.elasticsearch.action.count.CountRequest)
	 */
	@Override
	public ActionFuture<CountResponse> count(CountRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#count(org.elasticsearch.action.count.CountRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void count(CountRequest request,
			ActionListener<CountResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareCount(java.lang.String[])
	 */
	@Override
	public CountRequestBuilder prepareCount(String... indices) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#suggest(org.elasticsearch.action.suggest.SuggestRequest)
	 */
	@Override
	public ActionFuture<SuggestResponse> suggest(SuggestRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#suggest(org.elasticsearch.action.suggest.SuggestRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void suggest(SuggestRequest request,
			ActionListener<SuggestResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareSuggest(java.lang.String[])
	 */
	@Override
	public SuggestRequestBuilder prepareSuggest(String... indices) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#search(org.elasticsearch.action.search.SearchRequest)
	 */
	@Override
	public ActionFuture<SearchResponse> search(SearchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#search(org.elasticsearch.action.search.SearchRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void search(SearchRequest request,
			ActionListener<SearchResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareSearch(java.lang.String[])
	 */
	@Override
	public SearchRequestBuilder prepareSearch(String... indices) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#searchScroll(org.elasticsearch.action.search.SearchScrollRequest)
	 */
	@Override
	public ActionFuture<SearchResponse> searchScroll(SearchScrollRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#searchScroll(org.elasticsearch.action.search.SearchScrollRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void searchScroll(SearchScrollRequest request,
			ActionListener<SearchResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareSearchScroll(java.lang.String)
	 */
	@Override
	public SearchScrollRequestBuilder prepareSearchScroll(String scrollId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#multiSearch(org.elasticsearch.action.search.MultiSearchRequest)
	 */
	@Override
	public ActionFuture<MultiSearchResponse> multiSearch(
			MultiSearchRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#multiSearch(org.elasticsearch.action.search.MultiSearchRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void multiSearch(MultiSearchRequest request,
			ActionListener<MultiSearchResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareMultiSearch()
	 */
	@Override
	public MultiSearchRequestBuilder prepareMultiSearch() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#moreLikeThis(org.elasticsearch.action.mlt.MoreLikeThisRequest)
	 */
	@Override
	public ActionFuture<SearchResponse> moreLikeThis(MoreLikeThisRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#moreLikeThis(org.elasticsearch.action.mlt.MoreLikeThisRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void moreLikeThis(MoreLikeThisRequest request,
			ActionListener<SearchResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareMoreLikeThis(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public MoreLikeThisRequestBuilder prepareMoreLikeThis(String index,
			String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#termVector(org.elasticsearch.action.termvector.TermVectorRequest)
	 */
	@Override
	public ActionFuture<TermVectorResponse> termVector(TermVectorRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#termVector(org.elasticsearch.action.termvector.TermVectorRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void termVector(TermVectorRequest request,
			ActionListener<TermVectorResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareTermVector(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public TermVectorRequestBuilder prepareTermVector(String index,
			String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#multiTermVectors(org.elasticsearch.action.termvector.MultiTermVectorsRequest)
	 */
	@Override
	public ActionFuture<MultiTermVectorsResponse> multiTermVectors(
			MultiTermVectorsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#multiTermVectors(org.elasticsearch.action.termvector.MultiTermVectorsRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void multiTermVectors(MultiTermVectorsRequest request,
			ActionListener<MultiTermVectorsResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareMultiTermVectors()
	 */
	@Override
	public MultiTermVectorsRequestBuilder prepareMultiTermVectors() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#percolate(org.elasticsearch.action.percolate.PercolateRequest)
	 */
	@Override
	public ActionFuture<PercolateResponse> percolate(PercolateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#percolate(org.elasticsearch.action.percolate.PercolateRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void percolate(PercolateRequest request,
			ActionListener<PercolateResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#preparePercolate()
	 */
	@Override
	public PercolateRequestBuilder preparePercolate() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#multiPercolate(org.elasticsearch.action.percolate.MultiPercolateRequest)
	 */
	@Override
	public ActionFuture<MultiPercolateResponse> multiPercolate(
			MultiPercolateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#multiPercolate(org.elasticsearch.action.percolate.MultiPercolateRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void multiPercolate(MultiPercolateRequest request,
			ActionListener<MultiPercolateResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareMultiPercolate()
	 */
	@Override
	public MultiPercolateRequestBuilder prepareMultiPercolate() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareExplain(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ExplainRequestBuilder prepareExplain(String index, String type,
			String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#explain(org.elasticsearch.action.explain.ExplainRequest)
	 */
	@Override
	public ActionFuture<ExplainResponse> explain(ExplainRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#explain(org.elasticsearch.action.explain.ExplainRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void explain(ExplainRequest request,
			ActionListener<ExplainResponse> listener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#prepareClearScroll()
	 */
	@Override
	public ClearScrollRequestBuilder prepareClearScroll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#clearScroll(org.elasticsearch.action.search.ClearScrollRequest)
	 */
	@Override
	public ActionFuture<ClearScrollResponse> clearScroll(
			ClearScrollRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.elasticsearch.client.Client#clearScroll(org.elasticsearch.action.search.ClearScrollRequest, org.elasticsearch.action.ActionListener)
	 */
	@Override
	public void clearScroll(ClearScrollRequest request,
			ActionListener<ClearScrollResponse> listener) {
		// TODO Auto-generated method stub

	}

}
