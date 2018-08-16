package com.winterwell.es.client;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.winterwell.utils.Dep;
import com.winterwell.utils.Printer;
import com.winterwell.utils.time.TUnit;

public class SearchScrollRequestBuilderTest {

	@Test
	public void testGet() {
		BulkRequestBuilderTest brbt = new BulkRequestBuilderTest();
		brbt.testBulkIndexMany2();
		
		// now search them
		ESHttpClient esc = Dep.get(ESHttpClient.class);
		SearchRequestBuilder srb = esc.prepareSearch(brbt.INDEX);
		srb.setScroll(TUnit.MINUTE.dt);
		srb.setDebug(true);
		SearchResponse sr = srb.get();
		sr.check();		
		String scrollId = sr.getScrollId();
		Map<String, Object> jobj = sr.getParsedJson();
		long total = sr.getTotal();
		assert scrollId != null;	
		assert total > 20 : total;

		int cnt = 0;		
		{
			List<Map> hits = sr.getHits();			
			if (hits!=null) {
				cnt += hits.size();
				System.out.println(Printer.toString(hits, "\n   "));
			}
		}		
		
		while(true) {
			SearchScrollRequestBuilder ss = esc.prepareSearchScroll(scrollId);
			ss.setScroll(TUnit.MINUTE.dt);
			SearchResponse got = ss.get();
			got.check();
			List<Map> moreHits = got.getHits();
			cnt += moreHits.size();
			System.out.println(Printer.toString(moreHits, "\n   "));
			Map<String, Object> jobj2 = got.getParsedJson();
			System.out.println(got);
			scrollId = got.getScrollId();
			if (moreHits.isEmpty()) break;
		}
		
		assert cnt > 40;
	}

}
