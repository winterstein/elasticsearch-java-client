package com.winterwell.es.client;

import java.util.Map;

import org.junit.Test;

import com.winterwell.es.ESType;
import com.winterwell.es.client.admin.PutMappingRequestBuilder;
import com.winterwell.gson.FlexiGson;
import com.winterwell.utils.Dep;
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;

public class BulkRequestBuilderTest {


	@Test
	public void testBulkIndexKids() {
		Dep.setIfAbsent(FlexiGson.class, new FlexiGson());
		Dep.setIfAbsent(ESConfig.class, new ESConfig());
		ESConfig esconfig = Dep.get(ESConfig.class);
		if ( ! Dep.has(ESHttpClient.class)) Dep.setSupplier(ESHttpClient.class, false, ESHttpClient::new);
		init();
		ESHttpClient esc = Dep.get(ESHttpClient.class);

		{
			BulkRequestBuilder bulk = esc.prepareBulk();
			IndexRequestBuilder pi = esc.prepareIndex("testbulk", "parent", "p2");
			pi.setBodyMap(new ArrayMap("name", "Becca"));
			bulk.add(pi);
			bulk.get();
		}
		Utils.sleep(1500);
		
		BulkRequestBuilder bulk = esc.prepareBulk();
		IndexRequestBuilder pik = esc.prepareIndex("testbulk", "kid", "k2");
		pik.setBodyMap(new ArrayMap("name", "Joshi"));
		pik.setParent("p2");
		pik.get();
		bulk.add(pik);
		BulkResponse br = bulk.get();
		assert ! br.hasErrors();
		System.out.println(br.getJson());
		Utils.sleep(1500);
		
		Map<String, Object> got = esc.get("testbulk", "kid", "k1");
		System.out.println(got);
	}

	
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
		assert ! br.hasErrors();
		
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
		assert ! br.hasErrors();
		
		Utils.sleep(1500);
		
		Map<String, Object> got = esc.get("testbulk", "simple", "s_22");
		System.out.println(got);
	}

	
	private void init() {
		ESHttpClient esc = Dep.get(ESHttpClient.class);
		esc.admin().indices().prepareCreate("testbulk").get();
		
		PutMappingRequestBuilder pm = esc.admin().indices().preparePutMapping("testbulk", "kid");
		pm.setBodyMap(new ESType().setParentType("parent"));
		pm.get();
		
//		PutMappingRequestBuilder pm = esc.admin().indices().preparePutMapping("testbulk", "parent");
//		pm.setBodyMap(msrc);
//		pm.get();
		
	}
}
