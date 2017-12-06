package com.winterwell.es.client;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Future;

import org.eclipse.jetty.util.ajax.JSON;

import com.google.common.util.concurrent.ListenableFuture;
import com.winterwell.es.ESPath;
import com.winterwell.es.client.agg.Aggregation;
import com.winterwell.es.client.suggest.Suggester;
import com.winterwell.es.fail.ESException;
import com.winterwell.gson.FlexiGson;
import com.winterwell.gson.Gson;
import com.winterwell.gson.GsonBuilder;
import com.winterwell.gson.JsonElement;
import com.winterwell.gson.JsonSerializationContext;
import com.winterwell.gson.JsonSerializer;
import com.winterwell.gson.PlainGson;
import com.winterwell.gson.StandardAdapters;
import com.winterwell.utils.Dep;
import com.winterwell.utils.Printer;
import com.winterwell.utils.StrUtils;
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.containers.Containers;
import com.winterwell.utils.log.Log;
import com.winterwell.utils.web.WebUtils;
import com.winterwell.utils.web.WebUtils2;
import com.winterwell.web.FakeBrowser;
import com.winterwell.web.WebEx;

public class ESHttpRequest<SubClass, ResponseSubClass extends IESResponse> {

	/**
	 * @param fields e.g. _parent, _routing etc.
	 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-fields.html
	 */
	public SubClass setFields(String... fields) {
		String fieldsAsCSL = "";
		for (int x = 0 ; x < fields.length; x++){
			fieldsAsCSL = fieldsAsCSL + fields[x];
			if (x != fields.length - 1) fieldsAsCSL = fieldsAsCSL + ",";
		}
		params.put("fields", fieldsAsCSL);	return (SubClass) this;
	}
	
	public SubClass setPath(ESPath path) {
		if (path.indices!=null) setIndices(path.indices);
		if (path.type!=null) setType(path.type);
		if (path.id!=null) setId(path.id);
		return (SubClass) this;
	}

	protected String method;

	final ESHttpClient hClient;
	String[] indices;
	String type;
	String id;
	
	protected String[] getIndices() {
		return indices;
	}
	
	/**
	 * This becomes the body json. Can be String or Map
	 */
	protected Map<String,Object> body;
	protected String bodyJson;
	/**
	 * This is added to the end of the url path.
	 * e.g. the call might end up being /MyIndex/endpoint
	 */
	protected final String endpoint;
	
	protected Map<String, Object> body() {
		if (body==null) setBodyMap(new ArrayMap());
		return body;
	}
	
	/**
	 * The get params -- i.e. those parameters passed via the url.
	 * @see #src
	 */
	Map<String,Object> params = new ArrayMap();

	
	String bulkOpName;

	int retries;

	boolean debug;
	
	public SubClass setDebug(boolean debug) {
		this.debug = debug;
		return (SubClass) this;
	}
	
	/**
	 * By default, if a request fails, it fails. You can set it to retry once or twice before giving up.
	 * @param retries
	 */
	public void setRetries(int retries) {
		assert retries >= 0;
		this.retries = retries;
	}

	public SubClass setParent(String parentId) {
		params.put("parent", parentId);
		return (SubClass) this;
	}

	
	public SubClass setId(String id) {
		this.id = id;
		return (SubClass) this;
	}

	/**
	 * 
	 * @param hClient
	 * @param endpoint Can be null -- a lot of operations, e.g. index,  use the http method (PUT/DELETE etc)
	 * as a marker without having their own endpoint per se.
	 */
	public ESHttpRequest(ESHttpClient hClient, String endpoint) {
		this.hClient = hClient;
		assert hClient != null;
		this.endpoint = endpoint;
	}

	public Map<String,Object> getParams() {
		return params;
	}
	

	public SubClass setRouting(String routing) {
		assert ! "null".equals(routing);
		params.put("routing", routing);
		return (SubClass) this;
	}


	public SubClass setIndex(String idx) {
		assert idx == null || idx.equals(idx.toLowerCase()) 
				: "invalid_index_name_exception - ES requires lowercased index names: "+idx;
		return setIndices(idx);
	}

	Gson gson() {
		return hClient.config.getGson();
	}
	
	public SubClass setIndices(String... indices) {
		this.indices = indices;
		return (SubClass) this;
	}
	
	public SubClass setType(String type) {
		this.type = type;
		return (SubClass) this;
	}


	public ResponseSubClass get() {
		get2_safetyCheck();
		return processResponse(doExecute(hClient));
	}
	

	/**
	 * Does nothing by default. Sub-classes can over-ride to unwrap the response object
	 * @param response
	 * @return
	 */
	protected ResponseSubClass processResponse(ESHttpResponse response) {
		return (ResponseSubClass) response;
	}
	/**
	 * Check for necessary parameters. E.g. an index request needs
	 * an index-name, type, id and a document.
	 * Sub-classes should override to do anything.
	 */
	protected void get2_safetyCheck() {
		
	}
	
	@Override
	public String toString() {
		if (indices!=null && indices.length==1) {
			return getClass().getSimpleName()+"["+getUrl("")+"]";
		}
		return getClass().getSimpleName();		
	}

	/**
	 * Set the request body. The request body can only be set once.
	 * @param json
	 * @return this
	 * @see ESHttpRequest#setBodyMap(Map)
	 */
	public SubClass setBodyJson(String json) throws IllegalStateException {
		if (body!=null || bodyJson!=null) {
			throw new IllegalStateException(this+": Body can only be set once");
		}
		this.bodyJson = json;
		return (SubClass) this;
	}
	

	/**
	 * Set the request body. The request body can only be set once.
	 * @param msrc
	 * @return this
	 * @see #setBodyJson(String)
	 */
	public SubClass setBodyMap(Map msrc) throws IllegalStateException {
		if (body!=null || bodyJson!=null) {
			throw new IllegalStateException(this+": Body can only be set once");
		}
		body = msrc;
		return (SubClass) this;
	}
		

	/**
	 * Do it! Use a thread-pool to call async -- immediate response, future result.
	 */
	public ListenableFuture<ESHttpResponse> execute() {
		return hClient.executeThreaded(this);
		// NB this 4ends up at #doExecute(esjc)
	}

	StringBuilder getUrl(String server) {
		// see https://www.elastic.co/guide/en/elasticsearch/reference/current/multi-index.html
		StringBuilder url = new StringBuilder(server);
		if (indices==null) {
			url.append("/_all");
		} else {
			url.append("/");
			for(String idx : indices) {
				url.append(WebUtils.urlEncode(idx));
				url.append(",");
			}
			if (indices.length!=0) StrUtils.pop(url, 1);
		}
		if (type!=null) url.append("/"+WebUtils.urlEncode(type));
		if (id!=null) url.append("/"+WebUtils.urlEncode(id));
		if (endpoint!=null) {
			// NB: Only a few requests, such as get, don't need an endpoint
			url.append("/"+endpoint);		
		}
		return url;
	}


	/**
	 * 
	 * @return Can be null. The source json
	 */
	public String getBodyJson() {
		if (bodyJson!=null) return bodyJson;
		if (body==null) return null;
		// A vanilla convertor for handling our objects
		// -- no @class in the maps and lists -- with handling of ES Client internal objects.
		// This is DIFFERENT from #gson(), which is for handling the caller's objects.  
		Gson gson = GsonBuilder.safe()
				.registerTypeAdapter(Aggregation.class, StandardAdapters.IHASJSONADAPTER)
				.registerTypeAdapter(Suggester.class, StandardAdapters.IHASJSONADAPTER)
				.create();
		bodyJson = gson.toJson(body); 
//				TODO gson().toJson(body);
		// sanity check the json				
//		assert JSON.parse(srcJson) != null : srcJson;
		return bodyJson;
	}

	
	/**
	 * Actually execute the call.
	 * 
	 * NB: this can be over-ridden, to allow for "complex" requests.
	 * @param esHttpClient
	 * @return 
	 */
	protected ESHttpResponse doExecute(ESHttpClient esjc) {		
		Thread.currentThread().setName("ESHttpClient: "+this);
		String curl = "";
		try {
			// random load balancing (if we have multiple servers setup)
			String server = Utils.getRandomMember(esjc.servers);
			StringBuilder url = getUrl(server);

			// NB: FakeBrowser should close down the IO it uses
			FakeBrowser fb = new FakeBrowser();			//.setDebug(true);
			fb.setMaxDownload(-1); // Your data, your bandwidth, your call.
			fb.setTimeOut(esjc.config.esRequestTimeout); // 1 minute timeout
			// e.g. HEAD
			fb.setRequestMethod(method);
			
			String jsonResult;
			String srcJson = getBodyJson();
			if (srcJson!=null) {
				// add in the get params
				WebUtils2.addQueryParameters(url, params);
				// ?? encode the srcJson for url-encoding ??
				
				// DEBUG hack
				// NB: pretty=true was doc-as-upsert
				if (debug || esjc.debug) {
					curl = StrUtils.compactWhitespace("curl -X"+(method==null?"POST":method)+" '"+url+"' -d '"+srcJson+"'");
					Log.d("ES.curl", curl);
				}
				
				assert JSON.parse(srcJson) != null : srcJson;
				
				jsonResult = fb.post(url.toString(), FakeBrowser.MIME_TYPE_URLENCODED_FORM, srcJson);
								
			} else {
				assert body == null : body;
				// NB: create index is a bodyless post
//				assert ! "POST".equals(req.method) : "No body for post?! Call setSource() From: "+req;
//				// DEBUG hack
				if (debug || esjc.debug) {
					curl = StrUtils.compactWhitespace("curl -X"+(method==null?"GET":method)+" '"+url+"&pretty=true'");
					Log.d("ES.curl", curl);
				}

				jsonResult = fb.getPage(url.toString(), (Map)params);
			}
			
			ESHttpResponse r = new ESHttpResponse(this, jsonResult);
			return r;
		} catch(WebEx.E404 ex) {
			// e.g. a get for an unstored object (a common case)
			return new ESHttpResponse(this, ex);
		} catch(WebEx ex) {
			// Quite possibly a script error
			// e.g. 40X
			return new ESHttpResponse(this, wrapError(ex, this));
		} catch(Throwable ex) {
			throw wrapError(ex, this);
		}		
	}
	
	
	/**
	 * @param ex
	 * @param req 
	 * @return
	 */
	private RuntimeException wrapError(Throwable ex, ESHttpRequest req) {
		if (ex instanceof ESException) return (RuntimeException) ex;
		String msg = req==null? ex.getMessage() : req.getUrl("")+" "+ex.getMessage();
		ESException esex = new ESException(msg, ex);
		esex.request = req;
		return esex;
	}



}
