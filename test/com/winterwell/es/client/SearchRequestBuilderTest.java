package com.winterwell.es.client;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.winterwell.es.ESTest;
import com.winterwell.utils.Dep;

public class SearchRequestBuilderTest extends ESTest  {

	@Test
	public void testSetSize() {
		BulkRequestBuilderTest brbt = new BulkRequestBuilderTest();
		List<String> ids = brbt.testBulkIndexMany2();
		String index = BulkRequestBuilderTest.INDEX;
		// now search
		ESHttpClient esc = Dep.get(ESHttpClient.class);
		SearchRequestBuilder s = esc.prepareSearch(index);
		s.setSize(6);
		List<Map> hits = s.get().getHits();
		assert hits.size() == 6 : hits.size();
	}

}
