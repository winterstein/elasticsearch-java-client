package com.winterwell.es.client;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.jetty.util.ajax.JSON;

import com.google.common.util.concurrent.ListenableFuture;
import com.winterwell.gson.Gson;
import com.winterwell.gson.GsonBuilder;
import com.winterwell.gson.PlainGson;
import com.winterwell.utils.Dep;
import com.winterwell.utils.StrUtils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.web.WebUtils;

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

	protected String method;

	ESHttpClient hClient;
	String[] indices;
	String type;
	String id;
	/**
	 * This becomes the body json. Can be String or Map
	 */
	protected Map<String,Object> body;
	protected String bodyJson;
	protected String endpoint;
	
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

	public ESHttpRequest(ESHttpClient hClient) {
		this.hClient = hClient;
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
		return setIndices(idx);
	}

	Gson gson() {
		return hClient.config.gson;
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
		return processResponse(hClient.execute(this));
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
		return getClass().getSimpleName()+"["+getUrl("")+"]";
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
	}

	StringBuilder getUrl(String server) {
		StringBuilder url = new StringBuilder(server);
		if (indices!=null && indices.length!=0) {
			assert indices.length == 1 : this;
			url.append("/"+WebUtils.urlEncode(indices[0]));
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
		// TODO Do we need a vanilla convert - no @class in the maps and lists??
		Gson gson = gson();
		bodyJson = gson.toJson(body); 
//				TODO gson().toJson(body);
		// sanity check the json				
//		assert JSON.parse(srcJson) != null : srcJson;
		return bodyJson;
	}

	Gson plainGson() {
		return Dep.get(PlainGson.class).get();
	}


}
