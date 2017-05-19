package com.winterwell.es;

import static org.junit.Assert.*;

import org.junit.Test;

import com.winterwell.es.client.ESHttpClient;

public class ESTypeTest {

	@Test
	public void testNoIndex() {
		ESHttpClient ec = new ESHttpClient();
		new ESType().text();
	}

}
