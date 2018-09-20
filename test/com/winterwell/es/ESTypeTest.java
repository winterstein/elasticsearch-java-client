package com.winterwell.es;

import org.junit.Test;

import com.winterwell.es.client.ESHttpClient;

public class ESTypeTest {

	@Test
	public void testNoIndex() {
		ESHttpClient ec = new ESHttpClient();
		new ESType().text();
	}

}
