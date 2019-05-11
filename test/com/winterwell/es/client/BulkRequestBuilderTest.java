package com.winterwell.es.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.winterwell.es.ESType;
import com.winterwell.es.client.admin.PutMappingRequestBuilder;
import com.winterwell.gson.FlexiGson;
import com.winterwell.utils.Dep;
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;

public class BulkRequestBuilderTest {


	final static String INDEX = "testbulk";


	@Test
	public void testBulkIndexKids() {
		init();
		ESHttpClient esc = Dep.get(ESHttpClient.class);

		{
			BulkRequestBuilder bulk = esc.prepareBulk();
			IndexRequestBuilder pi = esc.prepareIndex(INDEX, "parent", "p2");
			pi.setBodyMap(new ArrayMap("name", "Becca"));
			bulk.add(pi);
			bulk.get();
		}
		Utils.sleep(1500);
		
		BulkRequestBuilder bulk = esc.prepareBulk();
		IndexRequestBuilder pik = esc.prepareIndex(INDEX, "kid", "k2");
		pik.setBodyMap(new ArrayMap("name", "Joshi"));
		pik.setParent("p2");
		pik.get();
		bulk.add(pik);
		BulkResponse br = bulk.get();
		assert ! br.hasErrors();
		System.out.println(br.getJson());
		Utils.sleep(1500);
		
		Map<String, Object> got = esc.get(INDEX, "kid", "k1");
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
		IndexRequestBuilder pi = esc.prepareIndex(INDEX, "simple", "s1");
		pi.setBodyMap(new ArrayMap("one", "a"));
		bulk.add(pi);
		
		BulkResponse br = bulk.get();
		assert ! br.hasErrors();
		
		Utils.sleep(1500);
		
		Map<String, Object> got = esc.get(INDEX, "simple", "s1");
		System.out.println(got);
	}


	@Test
	public void testBulkIndexMany() {
		// NB: tests must return void
		testBulkIndexMany2();
	}
	
	public List<String> testBulkIndexMany2() {
		Dep.setIfAbsent(FlexiGson.class, new FlexiGson());
		Dep.setIfAbsent(ESConfig.class, new ESConfig());
		ESConfig esconfig = Dep.get(ESConfig.class);
		if ( ! Dep.has(ESHttpClient.class)) Dep.setSupplier(ESHttpClient.class, false, ESHttpClient::new);
		init();
		ESHttpClient esc = Dep.get(ESHttpClient.class);

		List<String> ids = new ArrayList();
		BulkRequestBuilder bulk = esc.prepareBulk();
		for(int i=0; i<100; i++) {
			IndexRequestBuilder pi = esc.prepareIndex(INDEX, "simple", "s_"+i);			
			pi.setBodyMap(new ArrayMap("k", ""+i));
			bulk.add(pi);
			ids.add("s_"+i);
		}		
		bulk.setRefresh(KRefresh.WAIT_FOR);
		BulkResponse br = bulk.get();
		assert ! br.hasErrors();
		
		Map<String, Object> got = esc.get(INDEX, "simple", "s_22");
		System.out.println(got);
		return ids;
	}

	
	void init() {
		Dep.setIfAbsent(FlexiGson.class, new FlexiGson());
		Dep.setIfAbsent(ESConfig.class, new ESConfig());
		ESConfig esconfig = Dep.get(ESConfig.class);
		if ( ! Dep.has(ESHttpClient.class)) Dep.setSupplier(ESHttpClient.class, false, ESHttpClient::new);
		
		ESHttpClient esc = Dep.get(ESHttpClient.class);
		esc.admin().indices().prepareCreate(INDEX).get();
		
		PutMappingRequestBuilder pm = esc.admin().indices().preparePutMapping(INDEX, "kid");
		pm.setBodyMap(new ESType().setParentType("parent"));
		pm.get();	
	}
}
