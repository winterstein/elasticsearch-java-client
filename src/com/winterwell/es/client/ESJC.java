package com.winterwell.es.client;

/**
 * A shorthand convenience/marketing name for {@link ESHttpClient}
 * Welcome to ElasticSearch Java Client by Good-Loop!
 * 
 * Hm: this is silly -- we should either rename or not.
 * 
 * @author daniel
 *
 */
public final class ESJC extends ESHttpClient {

	public ESJC() {
		super();
	}
	
	public ESJC(ESConfig esc) {
		super(esc);
	}

}
