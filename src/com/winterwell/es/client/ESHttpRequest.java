package com.winterwell.es.client;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.jetty.util.ajax.JSON;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
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

	String method;

	ESHttpClient hClient;
	String[] indices;
	String type;
	String id;
	/**
	 * This becomes the body json. Can be String or Map
	 */
	Object src;
	String endpoint;

	Map<String,Object> params = new ArrayMap();

	/**
	 * @param excluded Can use wildcards, e.g. "*.bloat"
	 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/docs-get.html#get-source-filtering
	 */
	public SubClass setSourceExclude(String... excluded) {
		params.put("_source_exclude", StrUtils.join(excluded, ","));
		return (SubClass) this;
	}
	/**
	 * @param included Can use wildcards, e.g. "*.bloat"
	 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/docs-get.html#get-source-filtering
	 */
	public SubClass setSourceInclude(String... included) {
		params.put("_source_include", StrUtils.join(included, ","));
		return (SubClass) this;
	}

	
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
		return hClient.gson;
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
		return (ResponseSubClass) hClient.execute(this);
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

	public SubClass setSource(String json) {
		this.src = json;
		return (SubClass) this;
	}
	

	public SubClass setSource(Map msrc) {
		src = msrc;
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
		if (src==null) return null;
		String srcJson;
		if (src instanceof String) {
			srcJson = (String) src;
		} else {
			srcJson = JSON.toString(src);
		}	
		// sanity check the json				
		assert JSON.parse(srcJson) != null : srcJson;
		return srcJson;
	}


}
