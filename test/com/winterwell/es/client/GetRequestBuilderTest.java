package com.winterwell.es.client;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.winterwell.utils.Dep;
import com.winterwell.utils.Printer;
import com.winterwell.utils.time.TUnit;

public class GetRequestBuilderTest {

	@Test
	public void testGet() {
		BulkRequestBuilderTest brbt = new BulkRequestBuilderTest();
		List<String> ids = brbt.testBulkIndex100();
		
		// now get one
		ESHttpClient esc = Dep.get(ESHttpClient.class);
		GetRequestBuilder srb = new GetRequestBuilder(esc).setIndex(brbt.INDEX).setId(ids.get(0));
		srb.setDebug(true);
		GetResponse sr = srb.get();
		sr.check();		
		Long scrollId = sr.getVersion();
		Map<String, Object> obj = sr.getSourceAsMap();
		assert scrollId > 0;
	}

}
