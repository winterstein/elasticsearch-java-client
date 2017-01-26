package com.winterwell.es;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.junit.Test;

import com.winterwell.es.client.ESConfig;
import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.IESResponse;
import com.winterwell.es.client.IndexRequestBuilder;
import com.winterwell.es.client.SearchRequestBuilder;
import com.winterwell.es.client.SearchResponse;
import com.winterwell.utils.Printer;
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.time.Time;

public class ESUtilsTest {

	@Test
	public void testStartLocalES() throws IOException {
		ESConfig config = ESUtils.getConfig();
		config.port = 9200;
		Node client = ESUtils.startLocalES(9200, true, new File("tmp-data"));
		System.out.println(client);
		ESHttpClient c = new ESHttpClient(config);
		IndexRequestBuilder i = c.prepareIndex("test", "test_type", "testStartLocalES-"+Utils.getUID());
		i.setSource(new ArrayMap("hello","world", "time", new Time().toISOString()));
		IESResponse r = i.get();
		System.out.println(r);
		
		Utils.sleep(1000);
		
		SearchRequestBuilder search = c.prepareSearch("test");
		SearchResponse results = search.get();
		Printer.out(results.getHits());
		assert results.getHits().size() > 0;
		
		client.close();
	}

}
