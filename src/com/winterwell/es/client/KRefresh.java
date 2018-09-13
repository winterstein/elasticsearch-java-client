package com.winterwell.es.client;

/**
 * https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-refresh.html
 * 
 * NB: we can't use "true", so we upper-case 
 */
public enum KRefresh {
	TRUE, FALSE, WAIT_FOR
}
