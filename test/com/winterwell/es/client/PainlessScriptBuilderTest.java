package com.winterwell.es.client;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

import com.winterwell.es.ESPath;
import com.winterwell.utils.Dep;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.io.ConfigFactory;

public class PainlessScriptBuilderTest {

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
		UpdateRequestBuilder up = esjc.prepareUpdate(path);
		up.setScript(psb);
		foo
	}

}
