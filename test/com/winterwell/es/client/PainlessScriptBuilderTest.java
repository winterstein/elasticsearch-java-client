package com.winterwell.es.client;

import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

import com.winterwell.es.ESPath;
import com.winterwell.es.ESTest;
import com.winterwell.utils.Dep;
import com.winterwell.utils.containers.ArrayMap;

public class PainlessScriptBuilderTest extends ESTest  {

	@Test
	public void testSimpleMap() {
		PainlessScriptBuilder psb = new PainlessScriptBuilder();
		Map<String, Object> jsonObject = new ArrayMap(
				"a", "Apple", "n", 10, "x", 0.5);
		psb.setJsonObject(jsonObject);
		String script = psb.getScript();
		Map params = psb.getParams();
		System.out.println(script);
		System.out.println(params);
	}

	@Test
	public void testMapAndList() {
		PainlessScriptBuilder psb = new PainlessScriptBuilder();
		Map<String, Object> jsonObject = new ArrayMap(
				"a", new String[] {"Apple"}, 
				"n", Arrays.asList(10, 20));
		psb.setJsonObject(jsonObject);
		String script = psb.getScript();
		Map params = psb.getParams();
		System.out.println(script);
		System.out.println(params);
	}

	@Test
	public void testCallES() {
		BulkRequestBuilderTest brbt = new BulkRequestBuilderTest();
		brbt.testBulkIndex1();

		PainlessScriptBuilder psb = new PainlessScriptBuilder();
		Map<String, Object> jsonObject = new ArrayMap(
				"a", new String[] {"Apple"}, 
				"n", Arrays.asList(10, 20));
		psb.setJsonObject(jsonObject);
		String script = psb.getScript();
		Map params = psb.getParams();
		System.out.println(script);
		System.out.println(params);
		
		ESHttpClient esjc = Dep.get(ESHttpClient.class);
		
		ESPath path = new ESPath("test", "thingy", "testCallES");
		
		esjc.prepareDelete(path).setRefresh(KRefresh.TRUE).get();
		
		esjc.prepareIndex(path).setBodyDoc(new ArrayMap("b", "Bee"))
			.setRefresh("true")
			.get();
		
		UpdateRequestBuilder up = esjc.prepareUpdate(path);
		up.setRefresh(KRefresh.TRUE);
		up.setScript(psb);
		up.setDebug(true);
		IESResponse resp = up.get();
		resp.check();
		System.out.println(resp.getJson());
	}


	@Test
	public void testCallES_addToList() {
		BulkRequestBuilderTest brbt = new BulkRequestBuilderTest();
		brbt.testBulkIndex1();
		
		ESHttpClient esjc = Dep.get(ESHttpClient.class);
		
		ESPath path = new ESPath("test", "thingy", "testCallES_addToList");
		
		esjc.prepareDelete(path).setRefresh(KRefresh.TRUE).get();
		
		esjc.prepareIndex(path).setBodyDoc(
				new ArrayMap("a", new String[] {"Avocado"}, "n", Arrays.asList(20)))
			.setRefresh("true")
			.get();

		PainlessScriptBuilder psb = new PainlessScriptBuilder();
		Map<String, Object> jsonObject = new ArrayMap(
				"a", new String[] {"Apple"}, 
				"n", Arrays.asList(10, 20));
		psb.setJsonObject(jsonObject);
		
		UpdateRequestBuilder up = esjc.prepareUpdate(path);
		up.setRefresh(KRefresh.TRUE);
		up.setScript(psb);
		up.setDebug(true);
		IESResponse resp = up.get();
		resp.check();
		System.out.println(resp.getJson());
	}

	
}
