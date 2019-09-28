package com.winterwell.es.client;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.winterwell.es.UtilsForESTests;
import com.winterwell.es.client.admin.CreateIndexRequest;
import com.winterwell.utils.Dep;
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.log.Log;

public class ReindexRequestTest {

	@Test
	public void testReindexRequest() {
		UtilsForESTests.init();
		
		ESHttpClient es = Dep.get(ESHttpClient.class);
		CreateIndexRequest pi = es.admin().indices().prepareCreate("test1");
		pi.setRefresh(KRefresh.TRUE);
		pi.get();
		
		String i2 = "test2"+Utils.getRandomString(4);
		CreateIndexRequest pi2 = es.admin().indices().prepareCreate(i2);
		pi2.setRefresh(KRefresh.TRUE);
		pi2.get();
		
		IndexRequestBuilder i1 = es.prepareIndex("test1", "testtype", "testid1");
		i1.setBodyMap(new ArrayMap("msg", "Hello World! for "+i2));
		i1.setRefresh(KRefresh.TRUE);
		i1.get();
		
		ReindexRequest rr = new ReindexRequest(es, "test1", i2);
		rr.setDebug(true);
		IESResponse resp = rr.get();
		resp.check();

		GetRequestBuilder srb = new GetRequestBuilder(es).setIndex(i2).setId("testid1");
		srb.setDebug(true);
		GetResponse sr = srb.get();
		sr.check();		
		Map<String, Object> src = sr.getSourceAsMap();
		System.out.println(src);
	}

}
