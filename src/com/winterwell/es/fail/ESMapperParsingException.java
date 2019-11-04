package com.winterwell.es.fail;

import com.winterwell.web.WebEx;

public class ESMapperParsingException extends WebEx.E40X implements IElasticException {

	public ESMapperParsingException(String msg) {
		super(400, null, msg);
	}

	private static final long serialVersionUID = 1L;

}
