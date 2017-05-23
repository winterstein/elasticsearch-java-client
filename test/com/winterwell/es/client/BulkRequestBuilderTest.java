package com.winterwell.es.client;

import java.util.Map;

import org.junit.Test;

import com.winterwell.gson.FlexiGson;
import com.winterwell.utils.Dep;
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;

public class BulkRequestBuilderTest {

	@Test
	public void testBulkIndex1() {
		Dep.setIfAbsent(FlexiGson.class, new FlexiGson());
		Dep.setIfAbsent(ESConfig.class, new ESConfig());
		ESConfig esconfig = Dep.get(ESConfig.class);
		if ( ! Dep.has(ESHttpClient.class)) Dep.setSupplier(ESHttpClient.class, false, ESHttpClient::new);
		init();
		ESHttpClient esc = Dep.get(ESHttpClient.class);

		BulkRequestBuilder bulk = esc.prepareBulk();
		IndexRequestBuilder pi = esc.prepareIndex("testbulk", "simple", "s1");
		pi.setBodyMap(new ArrayMap("one", "a"));
		bulk.add(pi);
		
		BulkResponse br = bulk.get();
		assert ! br.hasFailures();
		
		Utils.sleep(1500);
		
		Map<String, Object> got = esc.get("testbulk", "simple", "s1");
		System.out.println(got);
	}


	@Test
	public void testBulkIndex100() {
		Dep.setIfAbsent(FlexiGson.class, new FlexiGson());
		Dep.setIfAbsent(ESConfig.class, new ESConfig());
		ESConfig esconfig = Dep.get(ESConfig.class);
		if ( ! Dep.has(ESHttpClient.class)) Dep.setSupplier(ESHttpClient.class, false, ESHttpClient::new);
		init();
		ESHttpClient esc = Dep.get(ESHttpClient.class);

		BulkRequestBuilder bulk = esc.prepareBulk();
		for(int i=0; i<100; i++) {
			IndexRequestBuilder pi = esc.prepareIndex("testbulk", "simple", "s_"+i);
			pi.setBodyMap(new ArrayMap("k", ""+i));
			bulk.add(pi);
		}		
		BulkResponse br = bulk.get();
		assert ! br.hasFailures();
		
		Utils.sleep(1500);
		
		Map<String, Object> got = esc.get("testbulk", "simple", "s_22");
		System.out.println(got);
	}

	
	private void init() {
		ESHttpClient esc = Dep.get(ESHttpClient.class);
		esc.admin().indices().prepareCreate("testbulk").get();
	}
}
