package com.winterwell.es.client.admin;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.winterwell.es.ESTest;
import com.winterwell.es.ESType;
import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.GetRequestBuilder;
import com.winterwell.es.client.GetResponse;
import com.winterwell.es.client.IESResponse;
import com.winterwell.es.client.IndexRequestBuilder;
import com.winterwell.es.fail.ESIndexAlreadyExistsException;
import com.winterwell.utils.Printer;
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;

public class PutMappingRequestBuilderTest extends ESTest {

	@Test
	public void testPutMappingRequestBuilder() {
		ESHttpClient esjc = getESJC();		
		// make an index
		String v = Utils.getRandomString(3);
		String idx = "test_mapping_"+v;
		CreateIndexRequest cir = esjc.admin().indices().prepareCreate(idx);
		cir.get().check();
		Utils.sleep(100);					
				
		// set a mapping
		String type = "mytype";
		PutMappingRequestBuilder pm = esjc.admin().indices().preparePutMapping(idx, type);
		ESType mytype = new ESType()
				.property("foo", ESType.keyword)
				.property("bar", new ESType().text());
		pm.setMapping(mytype);
		pm.setDebug(true);
		
		IESResponse resp = pm.get().check();
		Printer.out(resp.getJson());				
	}
	
	

	@Test
	public void testPutMappingRequestBuilderWithTypeName() {
		ESHttpClient esjc = getESJC();		
		// make an index
		String v = Utils.getRandomString(3);
		String idx = "test_mapping_"+v;
		CreateIndexRequest cir = esjc.admin().indices().prepareCreate(idx);
		cir.get().check();
		Utils.sleep(100);					
				
		// set a mapping
		String type = "mytype";
		PutMappingRequestBuilder pm = esjc.admin().indices().preparePutMapping(idx, type);
		ESType mytype = new ESType()
				.property("foo", ESType.keyword)
				.property("bar", new ESType().text());
		pm.setMapping(mytype);
		pm.setIncludeTypeName(true);
		pm.setDebug(true);
		
		IESResponse resp = pm.get().check();
		Printer.out(resp.getJson());				
	}

	@Test
	public void testMappingGood() {
		ESHttpClient esjc = getESJC();		
		// make an index
		String v = Utils.getRandomString(3);
		String idx = "test_mapping_"+v;
		CreateIndexRequest cir = esjc.admin().indices().prepareCreate(idx);
		cir.get().check();
		Utils.sleep(100);					
				
		// set a mapping
		PutMappingRequestBuilder pm = esjc.admin().indices().preparePutMapping(idx);
		ESType mytype = new ESType()
				.property("foo", ESType.keyword)
				.property("bar", new ESType().text());
		pm.setMapping(mytype);
		pm.setDebug(true);
		
		IESResponse resp = pm.get().check();
		
		// now index an item
		IndexRequestBuilder irb = esjc.prepareIndex(idx, "test_id_1");
		irb.setBodyDoc(new ArrayMap(
			"foo", "hello",
			"bar", "world"
		));
		irb.setDebug(true);
		IESResponse resp2 = irb.get().check();
		System.out.println(resp2);		
	}
	

	@Test
	public void testMappingBad() {
		ESHttpClient esjc = getESJC();		
		// make an index
		String v = Utils.getRandomString(3);
		String idx = "test_mapping_"+v;
		CreateIndexRequest cir = esjc.admin().indices().prepareCreate(idx);
		cir.get().check();
		Utils.sleep(100);					
				
		// set a mapping
		PutMappingRequestBuilder pm = esjc.admin().indices().preparePutMapping(idx);
		ESType mytype = new ESType()
				.property("foo", ESType.keyword)
				.property("bar", new ESType().INTEGER());
		pm.setMapping(mytype);
		pm.setDebug(true);
		
		IESResponse resp = pm.get().check();
		
		// now index an item
		try {
			IndexRequestBuilder irb = esjc.prepareIndex(idx, "test_id_1");
			irb.setBodyDoc(new ArrayMap(
				"foo", true,
				"bar", "Not a number"
			));
			irb.setDebug(true);
			IESResponse resp2 = irb.get().check();
			assert false;
		} catch(Exception ex) {
			// meant to fail :)
		}
	}
	
}
