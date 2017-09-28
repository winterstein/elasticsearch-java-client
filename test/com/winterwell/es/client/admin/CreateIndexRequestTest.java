package com.winterwell.es.client.admin;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.winterwell.es.client.ESConfig;
import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.IESResponse;
import com.winterwell.es.client.IndexRequestBuilder;
import com.winterwell.utils.Dep;
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.containers.Containers;

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
	
	@Test
	public void testDuplicate() {
		Dep.setIfAbsent(ESConfig.class, new ESConfig());
		ESConfig esconfig = Dep.get(ESConfig.class);
		if ( ! Dep.has(ESHttpClient.class)) Dep.setSupplier(ESHttpClient.class, false, ESHttpClient::new);

		ESHttpClient esc = Dep.get(ESHttpClient.class);
		
		{
			String v = Utils.getRandomString(3);
			String idx = "dupefoo_"+v;
			CreateIndexRequest cir = esc.admin().indices().prepareCreate(idx).setAlias("dupefoo");
			cir.get().check();
			
			String v2 = Utils.getRandomString(3);
			String idx2 = "dupefoo_"+v2;
			CreateIndexRequest cir2 = esc.admin().indices().prepareCreate(idx2).setAlias("dupefoo");
			cir2.get().check();
			
			IndexSettingsRequest rsettings = esc.admin().indices().indexSettings("dupefoo");
			Map<String, Object> settings = rsettings.get().getParsedJson();
			assert settings.size() > 1;
			esc.admin().indices().prepareDelete(idx, idx2).get();
		}
		
		{
			String sf = "solofoo_"+Utils.getRandomString(3);
			String v = Utils.getRandomString(3);
			String idx = sf+"_"+v;
			CreateIndexRequest cir = esc.admin().indices().prepareCreate(idx).setAlias(sf);
			cir.setFailIfAliasExists(true);
			cir.get().check();
			
			String v2 = Utils.getRandomString(3);
			String idx2 = sf+"_"+v2;
			CreateIndexRequest cir2 = esc.admin().indices().prepareCreate(idx2).setAlias(sf);
			cir2.setFailIfAliasExists(true);
			IESResponse r2 = cir2.get(); // fail
			assert ! r2.isSuccess();
			
			IndexSettingsRequest rsettings = esc.admin().indices().indexSettings(sf);
			Map<String, Object> settings = rsettings.get().getParsedJson();
			assert settings.size() == 1 : settings;
			assert Containers.only(settings.keySet()).equals(idx) : settings;
			
			esc.admin().indices().prepareDelete(idx, idx2).get();
		}
	}

}
