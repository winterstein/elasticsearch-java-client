package com.winterwell.es.fail;

import com.winterwell.web.WebEx;

public class MapperParsingException extends WebEx.E40X implements IElasticException {

	public MapperParsingException(String msg) {
		super(400, null, msg);
	}

	private static final long serialVersionUID = 1L;

}
