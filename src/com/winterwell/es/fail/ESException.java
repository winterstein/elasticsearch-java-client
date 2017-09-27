package com.winterwell.es.fail;

/**
 * Wrap e.g. IOExceptions, so that the higher-up code can catch and filter
 * that these are ES exceptions.
 * @author daniel
 *
 */
public class ESException extends RuntimeException {

	public ESException(String msg, Throwable ex) {
		super(msg, ex);
	}

	private static final long serialVersionUID = 1L;

}
