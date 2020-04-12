package com.winterwell.es.client.admin;

import java.util.Map;

import org.junit.Test;

import com.winterwell.es.ESTest;
import com.winterwell.es.client.ESConfig;
import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.IESResponse;
import com.winterwell.es.client.IndexRequestBuilder;
import com.winterwell.es.client.admin.CreateIndexRequest.Analyzer;
import com.winterwell.es.fail.ESException;
import com.winterwell.es.fail.ESIndexAlreadyExistsException;
import com.winterwell.utils.Dep;
import com.winterwell.utils.Printer;
import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.containers.Containers;
import com.winterwell.utils.io.SysOutCollectorStream;

public class CreateIndexRequestTest extends ESTest {

	@Test
	public void testES7UpgradeBug() {
		ESHttpClient esjc = getESJC();
		String baseIndex = "test_create_"+Utils.getRandomString(4);
		CreateIndexRequest pc = esjc.admin().indices().prepareCreate(baseIndex);			
//		actually, you can have multiple for all pc.setFailIfAliasExists(true); // this is synchronized, but what about other servers?
		pc.setDefaultAnalyzer(Analyzer.keyword);
		// aliases: index and index.all both point to baseIndex  
		// Set the query index here. The write one is set later as an atomic swap.			
		pc.setAlias("test_create_aliase");
		pc.setDebug(true);			
		IESResponse cres = pc.get();
		cres.check();
	}
	
	@Test
	public void testAlias() {
		ESHttpClient esc = getESJC();
		
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
	public void testDuplicateMakeBoth() {
		ESHttpClient esc = getESJC();
		
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
	}
	
	
	@Test
	public void testIndexAlreadyExists() {
		ESHttpClient esc = getESJC();
		
		try {
			String v = Utils.getRandomString(3);
			String idx = "testalready_"+v;
			CreateIndexRequest cir = esc.admin().indices().prepareCreate(idx);
			cir.get().check();
			Utils.sleep(100);
			
			CreateIndexRequest cir2 = esc.admin().indices().prepareCreate(idx);
			cir2.get().check();
			assert false;
		} catch(ESIndexAlreadyExistsException ex) {
			Printer.out(ex);
		}
	}
	
	@Test
	public void testDuplicateMakeFirstOnly() {
		ESHttpClient esc = getESJC();
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
			cir2.setDebug(true);
			IESResponse r2 = cir2.get(); // fail
			assert ! r2.isSuccess();
			
			Utils.sleep(1000); // wait a sec for the delete to clean up idx2
			assert ! esc.admin().indices().indexExists(idx2);
			IndexSettingsRequest rsettings = esc.admin().indices().indexSettings(sf);
			Map<String, Object> settings = rsettings.get().getParsedJson();
			assert settings.size() == 1 : settings;
			assert Containers.only(settings.keySet()).equals(idx) : settings.keySet();
			
			esc.admin().indices().prepareDelete(idx, idx2).get();
		}
	}

}
