package com.winterwell.es.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import org.eclipse.jetty.util.ajax.JSON;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.winterwell.es.ESPath;
import com.winterwell.es.client.admin.ClusterAdminClient;
import com.winterwell.es.client.admin.IndicesAdminClient;
import com.winterwell.es.client.admin.StatsRequest;
import com.winterwell.es.fail.ESException;
import com.winterwell.utils.Dep;
import com.winterwell.utils.StrUtils;
import com.winterwell.utils.Utils;
import com.winterwell.utils.WrappedException;
import com.winterwell.utils.log.Log;
import com.winterwell.utils.time.TUnit;
import com.winterwell.utils.web.WebUtils2;
import com.winterwell.web.ConfigException;
import com.winterwell.web.FakeBrowser;
import com.winterwell.web.WebEx;

/**
 * This object is thread safe. 
 * You can choose whether to get futures (via {@link #executeThreaded(ESHttpRequest)}), or just normal execute-in-the-current-thread
 * behaviour (via {@link #execute(ESHttpRequest)}).
 * @author daniel
 *
 */
public class ESHttpClient {

	
	public ESConfig getConfig() {
		return config;
	}
	
	/**
	 * You can optionally request a future.
	 */
	private static final ListeningExecutorService threads 
							= MoreExecutors.listeningDecorator(
									Executors.newFixedThreadPool(20));


	/**
	 * Call ES to check the connection is alive and well.
	 * @throws ConfigException
	 */
	public void checkConnection() {
		// try 3 times
		Throwable cause = null;
		for(int i=0; i<3; i++) {
			try {
				StatsRequest listreq = admin().indices().listIndices();
				ESHttpResponse listresponse = listreq.get().check();
				// all fine :)
				return;
			} catch(Throwable ex) {
				cause = ex;
				Log.w("ES", ex);
				Utils.sleep(100 + i*500);
			}
		}
		// fail!
		throw new ConfigException("Failed with settings: "+config, "ES", cause);
	}
	

	private List<String> servers;


	private boolean closed;


	final ESConfig config;


	@Deprecated // set on requests
	public static boolean debug = false;

	public void setServer(String server) {
		this.servers = Collections.singletonList(server);
	}
	
	public ESHttpClient() {
		this(Dep.get(ESConfig.class));
	}
	
	public ESHttpClient(ESConfig config) {
		this.config = config;
		if (config==null) throw new NullPointerException("null config for ES");
		String s = config.esUrl;		
		servers = Arrays.asList(s);
	}

	/**
	 * @deprecated Equivalent to {@link #admin().indices()}
	 */
	public IndicesAdminClient getIndicesAdminClient() {
		return admin().indices();
	}

	public AdminClient admin() {
		return new AdminClient();
	}
	
	public final class AdminClient {
		public IndicesAdminClient indices() {
			return new IndicesAdminClient(ESHttpClient.this);
		}

		public ClusterAdminClient cluster() {
			return new ClusterAdminClient(ESHttpClient.this);
		}
		
	}
	
	/**
	 * Use a thread-pool to call async -- immediate response, future result.
	 * @param req
	 * @return
	 */
	ListenableFuture<ESHttpResponse> executeThreaded(final ESHttpRequest req) {
		CallES call = new CallES(req);
		ListenableFuture<ESHttpResponse> future = threads.submit(call);
		return future;
	}
	
	/**
	 * Pass the call across threads. This will "preserve" stacktrace across threads for easier debugging.
	 */
	class CallES implements Callable<ESHttpResponse> {
		
		@Override
		public String toString() {
			return "CallES["+req+"]";
		}
		private ESHttpRequest req;
		StackTraceElement[] trace;
		
		CallES(ESHttpRequest req) {		
			assert req != null;
			this.req=req;
			// Keep the caller's stacktrace for reporting on errors
			if (debug || req.debug) {
				try {
					throw new Exception();
				} catch (Exception e) {
					trace = e.getStackTrace();
				}
			}
		}
		
		@Override
		public ESHttpResponse call() throws Exception {
			try {
				Thread.currentThread().setName("ESHttpClient (threaded): "+req);
				assert req.retries+1 >= 1;
				ESHttpResponse r = null;
				for(int t=0; t<req.retries+1; t++) {
					r = ESHttpClient.this.execute(req);
					// success?
					if (r.getError()==null) return r;
					// micro-pause before a retry to allow whatever the problem was to clear
					Utils.sleep(10);
				}
				// fail
				if (trace!=null) {				
					r.getError().setStackTrace(trace);
				}
				return r;
			} catch(Throwable ex) {
				// This shouldn't generate errors -- but if it does, don't let them just get lost!
				Log.e("ES", ex);
				throw Utils.runtime(ex);
			}
		}			
	}
	
	ESHttpResponse execute(ESHttpRequest req) {
		Thread.currentThread().setName("ESHttpClient: "+req);
		String curl = "";
		try {
			// random load balancing (if we have multiple servers setup)
			String server = Utils.getRandomMember(servers);
			StringBuilder url = req.getUrl(server);

			// NB: FakeBrowser should close down the IO it uses
			FakeBrowser fb = new FakeBrowser();			//.setDebug(true);
			fb.setMaxDownload(-1); // Your data, your bandwidth, your call.
			fb.setTimeOut(config.esRequestTimeout); // 1 minute timeout
			// e.g. HEAD
			fb.setRequestMethod(req.method);
			
			String jsonResult;
			String srcJson = req.getBodyJson();
			// Hack: some antivirus programs intercept HTTP PUT calls without bodies
			// (seen with ZF 2017)
			if (Utils.isBlank(srcJson) && "PUT".equals(req.method)) {
				srcJson = "{}";
			}
			// get/post the request
			if (srcJson!=null) {
				// add in the get params
				WebUtils2.addQueryParameters(url, req.params);
				// ?? encode the srcJson for url-encoding ??
				
				// DEBUG hack
				// NB: pretty=true was doc-as-upsert
				if (debug || req.debug) {
					curl = StrUtils.compactWhitespace("curl -X"+(req.method==null?"POST":req.method)+" '"+url+"' -d '"+srcJson+"'");
					Log.d("ES.curl", curl);
				}
				
				assert JSON.parse(srcJson) != null : srcJson;
				
				jsonResult = fb.post(url.toString(), FakeBrowser.MIME_TYPE_URLENCODED_FORM, srcJson);
								
			} else {
				// get
				assert req.body == null : req.body;
				// NB: a bodyless post, such as create index, does occur in ES
//				// DEBUG hack
				if (debug) {
					String fullurl = WebUtils2.addQueryParameters(url.toString(), req.params);
					curl = StrUtils.compactWhitespace("curl -X"+(req.method==null?"GET":req.method)+" '"+fullurl+"&pretty=true'");
					Log.d("ES.curl", curl);
				}

				jsonResult = fb.getPage(url.toString(), req.params);
			}
			
			ESHttpResponse r = new ESHttpResponse(req, jsonResult);
			return r;
		} catch(WebEx.E404 ex) {
			// e.g. a get for an unstored object (a common case)
			return new ESHttpResponse(req, ex);
		} catch(WebEx ex) {
			// Quite possibly a script error
			// e.g. 40X
			System.out.println(curl);
			return new ESHttpResponse(req, ex);
		} catch (WrappedException wex) {
			// report what url failed
			Throwable cause = wex.getCause();
			if (cause instanceof IOException) {
				throw new ESException(req.toString(), cause);
			}
			throw Utils.runtime(cause);
		} catch(Throwable ex) {
//			System.out.println(curl);
			throw Utils.runtime(ex);
		}	
	}
	
	

	@Override
	public String toString() {
		return "ESHttpClient[servers=" + servers + "]";
	}

	/**
	 * Prepare to index (aka store or insert) a document!
	 * @param index
	 * @param type
	 * @param id
	 * @return an IndexRequestBuilder Typical usage: call setSource(), then get()
	 */
	public IndexRequestBuilder prepareIndex(String index, String type, String id) {
		return prepareIndex(new ESPath(index, type, id));
	}

	public DeleteRequestBuilder prepareDelete(String esIndex, String esType, String id) {
		com.winterwell.es.client.DeleteRequestBuilder drb = new DeleteRequestBuilder(this);
		drb.setIndex(esIndex).setType(esType).setId(id);
		return drb;
	}

	/**
	 * Convenience for using {@link GetRequestBuilder} to get a document (with no routing)
	 * @return source-as-map, or null if not found
	 */
	public Map<String, Object> get(String index, String type, String id) {
		GetRequestBuilder gr = new GetRequestBuilder(this);
		gr.setIndex(index).setType(type).setId(id);
		gr.setSourceOnly(true);
		GetResponse r = gr.get();
		if ( ! r.isSuccess()) return null;
		return r.getSourceAsMap();
	}
	
	/**
	 * 
	 * @param path
	 * @return source-as-map, or null if not found
	 */
	public Map<String, Object> get(ESPath path) {
		return get(path.index(), path.type, path.id);
	}

	public <X> X get(String index, String type, String id, Class<X> class1) {
		GetRequestBuilder gr = new GetRequestBuilder(this);
		gr.setIndex(index).setType(type).setId(id);
		gr.setSourceOnly(true);
		GetResponse r = gr.get();
		if ( ! r.isSuccess()) return null;
		String json = r.getSourceAsString();
		X x = config.getGson().fromJson(json, class1);
		return x;
	}

	public <X> X get(ESPath path, Class<X> class1) {
		return get(path.index(), path.type, path.id, class1);
	}

	

	public SearchRequestBuilder prepareSearch(String index) {
		return new SearchRequestBuilder(this).setIndex(index);
	}

	public BulkRequestBuilder prepareBulk() {
		return new BulkRequestBuilder(this);
	}

	public SearchScrollRequestBuilder prepareSearchScroll(String scrollId) {
		return new SearchScrollRequestBuilder(this, scrollId, TUnit.MINUTE.dt);
	}
	
	public ClearScrollRequestBuilder prepareClearScroll() {
		return new ClearScrollRequestBuilder(this);
	}

	public void close() {
		if (closed) return;
//		threads.shutdown(); the threads are a shared static pool
		closed = true;
	}

	public UpdateRequestBuilder prepareUpdate(ESPath path) {
		UpdateRequestBuilder urb = new UpdateRequestBuilder(this);
		urb.setPath(path);
		return urb;
	}

	public IndexRequestBuilder prepareIndex(ESPath path) {
		IndexRequestBuilder urb = new IndexRequestBuilder(this);
		urb.setPath(path);
		return urb;
	}

	
}
