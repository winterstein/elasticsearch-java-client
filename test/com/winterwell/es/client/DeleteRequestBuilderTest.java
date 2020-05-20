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
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.web.WebEx;

public class DeleteRequestBuilderTest extends ESTest {

	@Test
	public void testPutGetDelete() {
		ESHttpClient esjc = getESJC();		
		// make an index
		String idx = "test_putgetdel";
		if ( ! esjc.admin().indices().indexExists(idx)) {
			CreateIndexRequest cir = esjc.admin().indices().prepareCreate(idx);
			cir.get().check();
			Utils.sleep(100);					
		}
		
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
		
		// and delete it!
		DeleteRequestBuilder drb = new DeleteRequestBuilder(esjc);
		drb.setIndex(idx).setId("test_id_1");
		drb.setDebug(true);
		drb.get().check();
		
		Utils.sleep(100);

		try {
			GetRequestBuilder gr2 = new GetRequestBuilder(esjc);
			gr2.setIndex(idx).setId("test_id_1");
			GetResponse r2 = (GetResponse) gr2.get().check();
			assert false : r2.getJson();
		} catch(WebEx.E404 good) {
			// :)
		}
	}
	
	

	@Test
	public void testDelete404() {
		ESHttpClient esjc = getESJC();		
		// make an index
		String idx = "test_putgetdel";
		if ( ! esjc.admin().indices().indexExists(idx)) {
			CreateIndexRequest cir = esjc.admin().indices().prepareCreate(idx);
			cir.get().check();
			Utils.sleep(100);					
		}
				
		// and delete it!?
		try {
			DeleteRequestBuilder drb = new DeleteRequestBuilder(esjc);
			drb.setIndex(idx).setId("test_id_never88");
			drb.setDebug(true);
			IESResponse res = drb.get().check();
			System.out.println(res);
			assert false;
		} catch(WebEx.E404 ok) {
			// OK
		}
	}
	
	@Test
	public void testDelete404_quiet() {
		ESHttpClient esjc = getESJC();		
		// make an index
		String idx = "test_putgetdel";
		if ( ! esjc.admin().indices().indexExists(idx)) {
			CreateIndexRequest cir = esjc.admin().indices().prepareCreate(idx);
			cir.get().check();
			Utils.sleep(100);					
		}
				
		// and delete it!?
		ESPath path = new ESPath(idx, "test_id_never89");
		DeleteRequestBuilder drb = esjc.prepareDelete(path);
		drb.setDebug(true);
		IESResponse res = drb.get().check();
		System.out.println(res);
	}

}
