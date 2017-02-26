package com.winterwell.es.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.util.ajax.JSON;

import com.winterwell.utils.StrUtils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.io.FileUtils;
import com.winterwell.utils.threads.IFuture;
import com.winterwell.utils.time.Dt;


/**
 * Update a document based on a script provided.
 * https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update.html#_literal_doc_as_upsert_literal
 * 
 * @see org.elasticsearch.action.update.UpdateRequestBuilder
 * @author daniel
 * @testedby UpdateRequestBuilderTest
 */
public class UpdateRequestBuilder extends ESHttpRequest<UpdateRequestBuilder,IESResponse> {

	private boolean docAsUpsert;
	
	@Override
	protected void get2_safetyCheck() {
		if (indices==null || indices.length==0) throw new IllegalStateException("No index specified for update: "+this);
		if (type==null) throw new IllegalStateException("No type specified for update: "+this);
		if (id==null) throw new IllegalStateException("No id specified for update: "+this);
	}
	
	public UpdateRequestBuilder(ESHttpClient esHttpClient) {
		super(esHttpClient);
		endpoint = "_update";
		method = "POST";
		body = new ArrayMap();
		bulkOpName = "update";
	}
	
	/**
     * The language of the script to execute.
     * Valid options are: mvel, js, groovy, python, expression, and native (Java)<br>
     * Default: groovy (v1.3; was mvel before, but mvel is now deprecated).<br>
     * There are security settings which affect scripts! See the link below for details.
     * <p>
     * Ref: http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/modules-scripting.html
     */
    public UpdateRequestBuilder setScriptLang(String scriptLang) {
    	params.put("lang", scriptLang);
        return this;
    }
	
	public UpdateRequestBuilder setDoc(Map doc) {
		body().put("doc", doc);
		return this;
	}

	/**
	 * @deprecated Use {@link #setDoc(Map)} or {@link #setScript(String)}
	 */
	@Override
	public UpdateRequestBuilder setSource(String json) {
		return super.setSource(json);
	}

	/**
	 * @deprecated Use {@link #setDoc(Map)} or {@link #setScript(String)}
	 */
	@Override
	public UpdateRequestBuilder setSource(Map msrc) {
		// TODO Auto-generated method stub
		return super.setSource(msrc);
	}
	
	/**
	 * Ref: https://www.elastic.co/guide/en/elasticsearch/reference/5.2/docs-update.html#_literal_doc_as_upsert_literal
	 * @param b
	 * @return
	 */
	public UpdateRequestBuilder setDocAsUpsert(boolean b) {
		if (b==docAsUpsert) return this;
		docAsUpsert = b;
		body.put("doc_as_upsert", docAsUpsert);
//		// move the doc/upsert data if it was set already
//		if (docAsUpsert) {
//			Object doc = body.get("doc");
//			body.put("upsert", doc);
//		} else {
//			Object doc = body.get("upsert");
//			body.put("doc", doc);
//		}
		return this;
	}

	/**
	 * WARNING: Whether or not this works will depend on your script settings!
	 * 
	 * Convenience method (not a part of the base API) for removing fields.
	 * ??Can this combine with upsert?? 
	 * @param removeTheseFields
	 * @param optionalResultField
	 * @return
	 */
	public UpdateRequestBuilder setRemovedFields(List<String> removeTheseFields, String optionalResultField) {		
		if (removeTheseFields.isEmpty()) {
			// Probably a no-op!
			return this;
		}
		assert getScript()==null;
		String script;
				// is there a tag to remove? Set OP_RESULT
//				"already = ctx._source.jobid2output[job]; "
//				+"if (already==empty) { ctx._source."+ESStorage._ESresult+" = false; } "
//				+"else { ctx._source."+ESStorage._ESresult+" = true; }"		
		script = FileUtils.read(UpdateRequestBuilder.class.getResourceAsStream("remove_fields.groovy"));
		setScript(script);
		setScriptParams(new ArrayMap("removals", removeTheseFields, "resultField", optionalResultField));
		// Return what we did
		if (optionalResultField!=null) setFields(optionalResultField);
		return this;
	}

	public UpdateRequestBuilder setUpsert(Map<String, Object> json) {
		// jetty JSON is slightly more readable
//		String _sjson = hClient.gson.toJson(json);
		String sjson = JSON.toString(json);
//		assert sjson.equals(_sjson);
		body.put("upsert", sjson);
		return this;
	}

	public UpdateRequestBuilder setScript(String script) {
//		String _json = hClient.gson.toJson(script);
		String json = JSON.toString(script);
//		assert json.equals(_json); can be different -- choices on encoding chars
		body.put("script", json);
		return this;
	}
	
	/**
	 * 
	 * @return Can be null
	 */
	public String getScript() {
		return (String) body.get("script");
	}


	public UpdateRequestBuilder setScriptParams(Map params) {
		body.put("params", JSON.toString(params));
		return this;
	}

	/**
	 * 
	 * @param ttl Can be null.
	 */
	public void setTimeToLive(Dt ttl) {
		if (ttl==null) params.remove("_ttl");
		else params.put("_ttl", ttl.getMillisecs());
	}

	public void setDoc(String docJson) {
		// HACK - poke the doc json into a wrapping doc property
		setSource("{\"doc\":"+docJson+"}");
	}




}
