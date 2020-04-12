package com.winterwell.es.client;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.winterwell.es.ESPath;
import com.winterwell.es.ESTest;
import com.winterwell.es.ESType;
import com.winterwell.es.UtilsForESTests;
import com.winterwell.es.client.admin.CreateIndexRequest;
import com.winterwell.es.client.admin.PutMappingRequestBuilder;
import com.winterwell.utils.Dep;
import com.winterwell.utils.Printer;
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.web.WebEx;

public class UpdateRequestBuilderTest extends ESTest {

	@Test
	public void testPutUpdate() {
		ESHttpClient esjc = getESJC();		
		// make an index
		String v = Utils.getRandomString(3);
		String idx = "test_put_"+v;
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
		irb.setRefresh(KRefresh.TRUE);
		IESResponse resp2 = irb.get().check();
		Utils.sleep(100);
		
		// and update it
		
		UpdateRequestBuilder up = esjc.prepareUpdate(new ESPath(idx, "test_id_1"));
		up.setDoc(new ArrayMap("bar", "Mars"));
		up.setDebug(true);
		IESResponse resp3 = up.get().check();
		Printer.out(resp3);
	}
	
}
