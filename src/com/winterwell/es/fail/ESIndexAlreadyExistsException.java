package com.winterwell.es.fail;

import com.winterwell.web.WebEx;

public class ESIndexAlreadyExistsException extends WebEx.E40X implements IElasticException {

	public ESIndexAlreadyExistsException(String msg) {
		super(400, null, msg);
	}

	private static final long serialVersionUID = 1L;

}
