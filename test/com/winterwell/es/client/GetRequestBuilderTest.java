package com.winterwell.es.client;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.winterwell.es.ESTest;
import com.winterwell.es.ESType;
import com.winterwell.es.UtilsForESTests;
import com.winterwell.es.client.admin.CreateIndexRequest;
import com.winterwell.es.client.admin.PutMappingRequestBuilder;
import com.winterwell.utils.Dep;
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.web.WebEx;

public class GetRequestBuilderTest extends ESTest {

	@Test
	public void testPutGet() {
		ESHttpClient esjc = getESJC();		
		// make an index
		String v = Utils.getRandomString(3);
		String idx = "test_putget_"+v;
		CreateIndexRequest cir = esjc.admin().indices().prepareCreate(idx);
		cir.get().check();
		Utils.sleep(100);					
		
		// now index an item
		IndexRequestBuilder irb = esjc.prepareIndex(idx, "test_id_1");
		irb.setBodyDoc(new ArrayMap(
			"foo", "hello",
			"bar", "world"
		));
		irb.setDebug(true);
		IESResponse resp2 = irb.get().check();
		System.out.println(resp2);
		
		Utils.sleep(1000);
		
		// and fetch it		
		GetRequestBuilder gr = new GetRequestBuilder(esjc);
		gr.setDebug(true);
		gr.setIndex(idx).setId("test_id_1");
		GetResponse r = (GetResponse) gr.get().check();
		System.out.println(r.getSourceAsMap());
	}
	
	
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
			UtilsForESTests.init();
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
