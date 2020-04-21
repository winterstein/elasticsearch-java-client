package com.winterwell.es.client;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.winterwell.es.ESPath;
import com.winterwell.es.ESTest;
import com.winterwell.es.client.admin.CreateIndexRequest;
import com.winterwell.es.client.query.ESQueryBuilder;
import com.winterwell.es.client.query.ESQueryBuilders;
import com.winterwell.utils.Printer;
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.time.Time;

public class UpdateByQueryRequestBuilderTest extends ESTest {


	@Test
	public void testUnsetQuery() {
		ESHttpClient esjc = getESJC();
		esjc.debug = true;
		// make an index
		String v = Utils.getRandomString(3);
		String idx = "test_put_"+v;
		CreateIndexRequest cir = esjc.admin().indices().prepareCreate(idx);
		cir.get().check();
		Utils.sleep(100);					
		
		// now index 2 items
		{
			IndexRequestBuilder irb = esjc.prepareIndex(idx, "test_id_1");
			irb.setBodyDoc(new ArrayMap(
				"foo", "hello 1",
				"bar", "world"
			));
			irb.setDebug(true);
			irb.setRefresh(KRefresh.TRUE);
			IESResponse resp2 = irb.get().check();
		}
		{
			IndexRequestBuilder irb = esjc.prepareIndex(idx, "test_id_2");
			irb.setBodyDoc(new ArrayMap(
				"foo", "hello 2"
			));
			irb.setDebug(true);
			irb.setRefresh(KRefresh.TRUE);
			IESResponse resp2 = irb.get().check();
		}		
		Utils.sleep(100);
		
		// and update the unset bar		
		UpdateByQueryRequestBuilder up = new UpdateByQueryRequestBuilder(esjc);
		up.setIndex(idx);
		up.debug = true;		
		ESQueryBuilder qb = ESQueryBuilders.boolQuery().mustNot(ESQueryBuilders.existsQuery("bar"));
		up.addQuery(qb);		
		up.setScript("ctx._source.bar=\""+new Time(2000,1,1).toISOString()+"\"");
		up.setScriptLang("painless");
		up.setDebug(true);
		up.setRefresh(KRefresh.TRUE);
		IESResponse resp3 = up.get().check();
		Printer.out(resp3);
		
//		Map<String, Object> got = esjc.get(path);
//		System.out.println(got);
//		assert got.get("bar").equals("Mars");
//		assert got.get("foo").equals("hello");
	}
	
}
