package com.winterwell.es.client;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.winterwell.utils.Dep;
import com.winterwell.web.WebEx;

public class GetRequestBuilderTest {

	@Test
	public void testGet() {
		BulkRequestBuilderTest brbt = new BulkRequestBuilderTest();
		List<String> ids = brbt.testBulkIndexMany2();
		
		// now get one
		ESHttpClient esc = Dep.get(ESHttpClient.class);
		GetRequestBuilder srb = new GetRequestBuilder(esc).setIndex(brbt.INDEX).setId(ids.get(0));
		srb.setDebug(true);
		GetResponse sr = srb.get();
		sr.check();		
		Long scrollId = sr.getVersion();
		Map<String, Object> obj = sr.getSourceAsMap();
		assert scrollId > 0;
	}


	@Test
	public void testGet404() {
		try {
			BulkRequestBuilderTest brbt = new BulkRequestBuilderTest();
			brbt.init();
			// now get one
			ESHttpClient esc = Dep.get(ESHttpClient.class);
			GetRequestBuilder srb = new GetRequestBuilder(esc).setIndex(brbt.INDEX).setId("no_nevermadethisthingever");
			srb.setDebug(true);
			GetResponse sr = srb.get();
			assert ! sr.isSuccess();
			sr.check();
			assert false : sr;
		} catch(WebEx.E404 ex) {
			// all good
		}
	}

}
