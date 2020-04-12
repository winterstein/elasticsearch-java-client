package com.winterwell.es;

import com.winterwell.es.client.BulkRequestBuilderTest;
import com.winterwell.es.client.ESConfig;
import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.admin.PutMappingRequestBuilder;
import com.winterwell.gson.FlexiGson;
import com.winterwell.utils.Dep;

public class UtilsForESTests {

	public static void init() {
		Dep.setIfAbsent(FlexiGson.class, new FlexiGson());
		Dep.setIfAbsent(ESConfig.class, new ESConfig());
		ESConfig esconfig = Dep.get(ESConfig.class);
		if ( ! Dep.has(ESHttpClient.class)) Dep.setSupplier(ESHttpClient.class, false, ESHttpClient::new);
		
		ESHttpClient esc = Dep.get(ESHttpClient.class);
		esc.admin().indices().prepareCreate(BulkRequestBuilderTest.INDEX).get();
		
		PutMappingRequestBuilder pm = esc.admin().indices().preparePutMapping(BulkRequestBuilderTest.INDEX, "kid");
		pm.setBodyMap(new ESType().setParentType("parent"));
		pm.get();	
	}

}
