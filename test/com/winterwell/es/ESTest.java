package com.winterwell.es;

import org.junit.BeforeClass;

import com.winterwell.es.client.ESConfig;
import com.winterwell.es.client.ESHttpClient;
import com.winterwell.utils.Dep;
import com.winterwell.utils.Printer;
import com.winterwell.utils.io.ConfigFactory;
import com.winterwell.utils.io.ConfigFactoryTest;

public class ESTest {

	@BeforeClass
	public static void setupES() {
		ESConfig config = ConfigFactory.get().getConfig(ESConfig.class);
		Printer.out(config);
	}
	
	protected ESHttpClient getESJC() {
//		Dep.setIfAbsent(ESConfig.class, new ESConfig()); done in setupES
//		ESConfig esconfig = Dep.get(ESConfig.class);
		if ( ! Dep.has(ESHttpClient.class)) {
			Dep.setSupplier(ESHttpClient.class, false, ESHttpClient::new);
		}
		ESHttpClient esc = Dep.get(ESHttpClient.class);
		return esc;
	}
	
}
