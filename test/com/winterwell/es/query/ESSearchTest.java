package com.winterwell.es.query;

import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;

import com.winterwell.es.ESType;
import com.winterwell.es.ESUtils;
import com.winterwell.es.client.ESConfig;
import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.IndexRequestBuilder;
import com.winterwell.es.client.SearchRequestBuilder;
import com.winterwell.es.client.SearchResponse;
import com.winterwell.es.client.admin.CreateIndexRequest;
import com.winterwell.es.client.admin.PutMappingRequestBuilder;
import com.winterwell.gson.FlexiGson;
import com.winterwell.utils.Dep;
import com.winterwell.utils.containers.ArrayMap;

public class ESSearchTest {

	private String index = "testsearch";

	@Test
	public void testSearchParentChild() {
		Dep.setIfAbsent(FlexiGson.class, new FlexiGson());
		Dep.setIfAbsent(ESConfig.class, new ESConfig());
		ESConfig esconfig = Dep.get(ESConfig.class);
		if ( ! Dep.has(ESHttpClient.class)) Dep.setSupplier(ESHttpClient.class, false, ESHttpClient::new);

		init();
		ESHttpClient esc = Dep.get(ESHttpClient.class);
		
		{
			Map<String, Object> topMap = new ArrayMap("p1", "Becca");
			List<Map<String, Object>> kvs = ESUtils.assocListFromMap(topMap, "k", "v");
			// ...save that
			IndexRequestBuilder pi = esc.prepareIndex(index, "topmap", "parent1");
			pi.setBodyMap(new ArrayMap("kvs", kvs));
			pi.execute();
			// and a kid
			IndexRequestBuilder pi2 = esc.prepareIndex(index, "mapbit", "kid1");
			pi2.setParent("parent1");
			Map<String, Object> ktopMap = new ArrayMap("name", "Joshi");
			List<Map<String, Object>> kkvs = ESUtils.assocListFromMap(ktopMap, "k", "v");
			pi2.setBodyMap(new ArrayMap("kvs", kkvs));
			pi2.execute();		
		}
		{
			Map<String, Object> topMap = new ArrayMap("p2", "Annabel");
			IndexRequestBuilder pi = esc.prepareIndex(index, "topmap", "parent2");
			List<Map<String, Object>> kvs = ESUtils.assocListFromMap(topMap, "k", "v");
			pi.setBodyMap(new ArrayMap("kvs", kvs));
			pi.execute();
			// and a kid
			Map<String, Object> ktopMap = new ArrayMap("name", "Xander");
			List<Map<String, Object>> kkvs = ESUtils.assocListFromMap(ktopMap, "k", "v");
			IndexRequestBuilder pi2 = esc.prepareIndex(index, "mapbit", "kid1");
			pi2.setParent("parent2");
			pi2.setBodyMap(new ArrayMap("kvs", kkvs));
			pi2.execute();		
		}
		
		
		// search for same
		SearchRequestBuilder s = esc.prepareSearch(index);
		// Eclipse is upset about classes here??
		QueryBuilder q = new MapQueryBuilder(new ArrayMap(
				"parent_id", new ArrayMap(
						"type", "mapbit",
						"id", "parent1"
						)
				));
		// TODO add a min count or recent filter
		s.setQuery(q);
		// TODO scroll to get all results :(
//		// how many before we have to use a scroll??
		s.setSize(10000);
		SearchResponse response = s.get();
		List<Map> hits = response.getHits();
		// TODO assemble maps
		System.out.println(hits);
	}
	
	
	
	public void init() {
		// make index
		ESHttpClient esc = Dep.get(ESHttpClient.class);
		CreateIndexRequest pc = esc.admin().indices().prepareCreate(index );
		pc.get(); // no check: fails naturally if index already exists
		// mapbit
		PutMappingRequestBuilder putMapping2 = esc.admin().indices().preparePutMapping(index, "mapbit");
		putMapping2.setMapping(
				new ESType()
					.setParentType("topmap")
					.property("k", new ESType().text().keyword().noIndex())
					.property("v", new ESType().DOUBLE().noIndex())
				);
		putMapping2.get().check();
		// topmap
		PutMappingRequestBuilder putMapping1 = esc.admin().indices().preparePutMapping(index, "topmap");
		putMapping1.setMapping(new ESType().property("kvs", 
				new ESType()
					.property("k", new ESType().text().keyword().noIndex())
					.property("v", new ESType().DOUBLE().noIndex())
				)
			);
		putMapping1.get().check();
	}
		
}
