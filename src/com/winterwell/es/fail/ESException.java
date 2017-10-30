package com.winterwell.es.fail;

import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.utils.WrappedException;

/**
 * Wrap e.g. IOExceptions, so that the higher-up code can catch and filter
 * that these are ES exceptions.
 * 
 * TODO in multi-thread usage, ES client logs the caller, so can this report the caller stack??
 * @author daniel
 *
 */
public class ESException extends WrappedException {

	public transient ESHttpRequest request;

	public ESException(String msg, Throwable ex) {
		super(msg, ex);
	}

	private static final long serialVersionUID = 1L;

}
