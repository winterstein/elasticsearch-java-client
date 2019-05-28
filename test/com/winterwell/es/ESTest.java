package com.winterwell.es;

import org.junit.BeforeClass;

import com.winterwell.es.client.ESConfig;
import com.winterwell.utils.Printer;
import com.winterwell.utils.io.ConfigFactory;
import com.winterwell.utils.io.ConfigFactoryTest;

public class ESTest {

	@BeforeClass
	public static void setupES() {
		ESConfig config = ConfigFactory.get().getConfig(ESConfig.class);
		Printer.out(config);
	}
	
}
