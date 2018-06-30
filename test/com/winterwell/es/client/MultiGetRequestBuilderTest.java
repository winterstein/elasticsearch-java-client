package com.winterwell.es.client;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.winterwell.es.ESPath;
import com.winterwell.utils.Dep;
import com.winterwell.utils.Printer;
import com.winterwell.utils.time.TUnit;

public class MultiGetRequestBuilderTest {

	@Test
	public void testGet() {
		BulkRequestBuilderTest brbt = new BulkRequestBuilderTest();
		List<String> ids = brbt.testBulkIndexMany2();
		
		// now get two
		ESHttpClient esc = Dep.get(ESHttpClient.class);
		MultiGetRequestBuilder srb = new MultiGetRequestBuilder(esc).setIndex(brbt.INDEX);
		srb.addDoc(new ESPath(brbt.INDEX, "simple", ids.get(0)));
		srb.addDoc(new ESPath(brbt.INDEX, "simple", ids.get(1)));
		srb.setDebug(true);
		IESResponse sr = srb.get();
		sr.check();		
		Map<String, Object> jobj = sr.getParsedJson();
		System.out.println(jobj);
	}

	@Test
	public void testGetBadId() {
		BulkRequestBuilderTest brbt = new BulkRequestBuilderTest();
		List<String> ids = brbt.testBulkIndexMany2(); // cos: init
		
		// now get two
		ESHttpClient esc = Dep.get(ESHttpClient.class);
		MultiGetRequestBuilder srb = new MultiGetRequestBuilder(esc).setIndex(brbt.INDEX);
		srb.addDoc(new ESPath(brbt.INDEX, "simple", "bogus_doofus"));
		srb.setDebug(true);
		IESResponse sr = srb.get();
		sr.check();		
		Map<String, Object> jobj = sr.getParsedJson();
		System.out.println(jobj);
	}
}
