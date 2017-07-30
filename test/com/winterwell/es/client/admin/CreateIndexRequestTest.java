package com.winterwell.es.client.admin;

import static org.junit.Assert.*;

import org.junit.Test;

import com.winterwell.es.client.ESConfig;
import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.IndexRequestBuilder;
import com.winterwell.utils.Dep;
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;

public class CreateIndexRequestTest {

	@Test
	public void testAlias() {
		Dep.setIfAbsent(ESConfig.class, new ESConfig());
		ESConfig esconfig = Dep.get(ESConfig.class);
		if ( ! Dep.has(ESHttpClient.class)) Dep.setSupplier(ESHttpClient.class, false, ESHttpClient::new);

		ESHttpClient esc = Dep.get(ESHttpClient.class);
		
		{
			String v = Utils.getRandomString(3);
			String idx = "foo_"+v;
			CreateIndexRequest cir = esc.admin().indices().prepareCreate(idx).setAlias("foo");
			cir.get().check();
			
			IndexRequestBuilder pi = esc.prepareIndex(idx, "mytype", "id1");
			pi.setBodyMap(new ArrayMap("iam", "id1 in "+idx));
			pi.get().check();
		}
		{
			String v = Utils.getRandomString(3);
			String idx = "foo_"+v;
			CreateIndexRequest cir = esc.admin().indices().prepareCreate(idx).setAlias("foo");
			cir.get().check();
			
			IndexRequestBuilder pi = esc.prepareIndex(idx, "mytype", "id2");
			pi.setBodyMap(new ArrayMap("iam", "id2 in "+idx));
			pi.get().check();
		}		
		try {
			IndexRequestBuilder pi = esc.prepareIndex("foo", "mytype", "id3");
			pi.setBodyMap(new ArrayMap("iam", "id3 in foo"));
			pi.get().check();
			assert false;
		} catch(Exception ex) {
			System.out.println(ex);
		}
	}

}
