package com.winterwell.es.client;

import java.util.List;
import java.util.Map;

import org.eclipse.jetty.util.ajax.JSON;

import com.winterwell.es.client.agg.AggregationResults;
import com.winterwell.gson.Gson;
import com.winterwell.gson.GsonBuilder;
import com.winterwell.utils.MathUtils;
import com.winterwell.utils.StrUtils;
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.Containers;
import com.winterwell.utils.log.Log;
import com.winterwell.web.WebEx;

/**
 * Imitates {@link GetResponse}
 * @author daniel
 *
 */
public class ESHttpResponse implements IESResponse, SearchResponse, BulkResponse, GetResponse {

	private final String json;
	private final WebEx error;
	private final ESHttpRequest req;
	private Map parsed;

	/* (non-Javadoc)
	 * @see com.winterwell.es.client.IESResponse#toString()
	 */
	@Override
	public String toString() {
		return "ESHttpResponse["+StrUtils.ellipsize(Utils.or(json, ""+error), 120)+"]";
	}
	
	/**
	 * For use by wrapper sub-classes
	 * @param response
	 */
	protected ESHttpResponse(ESHttpResponse response) {
		this.req = response.req;
		this.json = response.json;
		this.error = response.error;
	}
	
	public ESHttpResponse(ESHttpRequest req, String json) {
		this.req = req;
		this.json = json;
		this.error = null;
	}

	public ESHttpResponse(ESHttpRequest req, WebEx ex) {
		this.error = ex;
		this.req = req;
		this.json = null;
	}

	/* (non-Javadoc)
	 * @see com.winterwell.es.client.IESResponse#isSuccess()
	 */
	@Override
	public boolean isSuccess() {
		return error==null;
	}

	@Override
	public Map<String, Object> getSourceAsMap() {
		Map<String, Object> map = getParsedJson();
		// is it just the source?
		if (req instanceof GetRequestBuilder && ((GetRequestBuilder)req).sourceOnly) {
			return map;
		}
		Object source = map.get("_source");
		return (Map) source;
	}
	
	public Object getFromResultFields(String input) {
		Map<String, Object> map = getParsedJson();
		// is it just the source?
		Object get = map.get("get");
		Object fields = ((Map) get).get("fields");
		return ((Map) fields).get(input);
	}
	
	public Map<String, Object> getParsedJson() {
		if (parsed!=null) return parsed;
		parsed = gson().fromJson(json, Map.class);
		return parsed;
	}
	
	public Map<String, Object> getJsonMap() {
		Map map = plainGson().fromJson(json, Map.class);
		return map;
	}
	
	
	
	private Gson plainGson() {
		Gson gb = new GsonBuilder()
						.setClassProperty(null)
						.create();
		return gb;
	}

	/**
	 * The raw json as returned by ES.
	 */
	public String getJson() {
		return json;
	}
	
	private Gson gson() {
		return req.hClient.config.getGson();
	}

	/* (non-Javadoc)
	 * @see com.winterwell.es.client.IESResponse#isAcknowledged()
	 */
	@Override
	public boolean isAcknowledged() {
		return isSuccess();
	}

	@Override
	public List getField(String name) {
		Map<String, Object> map = getFieldsFromGet();
		Object output = map.get(name);
		return (List) output;
	}
	
	@Override
	public String getSourceAsString() {
		// is it just the source?
		if (req instanceof GetRequestBuilder && ((GetRequestBuilder)req).sourceOnly) {
			return json;
		}
		Map<String, Object> map = getParsedJson();
		Object source = map.get("_source");
		return gson().toJson(source);
	}

	// From BulkResponse
	@Override
	public boolean hasErrors() {		
		if ( ! isSuccess()) {			
			return true;
		}
		Map<String, Object> map = getParsedJson();
		Object fails = map.get("errors"); // TODO Out of date?!
		if (Utils.yes(fails)) {
			return true;
		}
		return false;
	}
	
	public WebEx getError() {
		return error;
	}
	
	
	@Override
	public ESHttpResponse check() {
		if (error!=null) {
			Log.d("fail.check", "throw from "+req);
			throw error;
		}
		return this;
	}
	

	@Override
	public List<Map> getHits() {
		if ( ! isSuccess()) throw error;
		Map<String, Object> map = getParsedJson();
		Map hits = (Map) map.get("hits");
		Object hitsList = hits.get("hits");
		return (List<Map>) hitsList;
	}

	@Override
	public <X> List<X> getSearchResults() {
		List<Map> hits = getHits();
		List results = Containers.apply(hits, hit -> hit.get("_source"));
		return results;
	}

	
	@Override
	public Map getAggregations() {
		if ( ! isSuccess()) throw error;
		Map<String, Object> map = getParsedJson();
		Map hits = (Map) map.get("aggregations");
		return hits;
	}
	
	@Override
	public AggregationResults getAggregationResults(String aggName) {
		Map rs = (Map) getAggregations().get(aggName);
		return new AggregationResults(aggName, rs);
	}

	/**
	 * @return Never null - can throw exceptions
	 */
	@Override
	public long getTotal() throws IllegalArgumentException {
		if ( ! isSuccess()) throw error;
		Map<String, Object> map = getParsedJson();
		Map hits = (Map) map.get("hits");
		if (hits == null){
			throw new IllegalArgumentException("hits field cannot be null");
		}
		Number hitTotal = (Number) hits.get("total");
		if (hitTotal == null){
			throw new IllegalArgumentException("hitTotal field cannot be null");
		}
		return hitTotal.longValue();
	}

	
	@Override
	public Map getFacets() {
		if ( ! isSuccess()) throw error;
		Map<String, Object> map = getParsedJson();
		Object hits = map.get("facets");
		return (Map) hits;
	}

	@Override
	public Map getFieldsFromGet() {
		if (error!=null) throw error;
		Map<String, Object> map = getParsedJson();
		Map<String, Object> get = (Map<String, Object>) map.get("get");
		Map<String, Object> hits = (Map<String, Object>) (Map<String, Object>) get.get("fields");
		return hits;
	}
	
	@Override
	public String getScrollId() {
		if ( ! isSuccess()) throw error;
		Map<String, Object> map = getParsedJson();
		Object sid = map.get("_scroll_id");
		return (String) sid;
	}
}
