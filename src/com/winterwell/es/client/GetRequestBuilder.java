package com.winterwell.es.client;

public class GetRequestBuilder extends ESHttpRequest<GetRequestBuilder,GetResponse> {
	
	boolean sourceOnly;

	public GetRequestBuilder(ESHttpClient hClient) {
		super(hClient);
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
