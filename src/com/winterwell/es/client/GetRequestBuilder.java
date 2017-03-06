package com.winterwell.es.client;

import com.winterwell.utils.StrUtils;

public class GetRequestBuilder extends ESHttpRequest<GetRequestBuilder,GetResponse> {
	
	boolean sourceOnly;

	public GetRequestBuilder(ESHttpClient hClient) {
		super(hClient);
	}


	/**
	 * @param excluded Can use wildcards, e.g. "*.bloat"
	 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/docs-get.html#get-source-filtering
	 * @return 
	 */
	public GetRequestBuilder setResultsSourceExclude(String... excluded) {
		params.put("_source_exclude", StrUtils.join(excluded, ","));
		return this;
	}
	/**
	 * @param included Can use wildcards, e.g. "*.bloat"
	 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/docs-get.html#get-source-filtering
	 * @return 
	 */
	public GetRequestBuilder setResultsSourceInclude(String... included) {
		params.put("_source_include", StrUtils.join(included, ","));
		return this;
	}

	
	 /**
     * Sets the preference to execute the search. Defaults to randomize across shards. Can be set to
     * <tt>_local</tt> to prefer local shards, <tt>_primary</tt> to execute only on primary shards, or
     * a custom value, which guarantees that the same order will be used across different requests.
     */
    public GetRequestBuilder setPreference(String preference) {
    	params.put("preference", "_local");
        return this;
    }
    
    @Override
    StringBuilder getUrl(String server) {
    	// c.f. http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/docs-get.html#type
    	if (type==null) type = "_all";
    	StringBuilder url = super.getUrl(server);
    	if (sourceOnly) url.append("/_source");
    	return url;
    }

    /**
     * If true, only return the item _source json, without the surrounding score and other metadata.
     */
	public void setSourceOnly(boolean b) {
		sourceOnly = b;
	}

}
