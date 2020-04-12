package com.winterwell.es.fail;

import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.utils.WrappedException;
import com.winterwell.web.WebEx;

/**
 * Wrap e.g. IOExceptions, so that the higher-up code can catch and filter
 * that these are ES exceptions.
 * 
 * Note: ESJC *also* throws other exceptions, derived from {@link WebEx}
 * Both kinds implement the marker interface IElasticException  
 * 
 * TODO in multi-thread usage, ES client logs the caller, so can this report the caller stack??
 * @author daniel
 *
 */
public class ESException extends WrappedException implements IElasticException {

	public transient ESHttpRequest request;

	public ESException(String msg, Throwable ex) {
		super(msg, ex);
	}

	public ESException(String msg) {
		super(msg, null);
	}
	

	private static final long serialVersionUID = 1L;

}
