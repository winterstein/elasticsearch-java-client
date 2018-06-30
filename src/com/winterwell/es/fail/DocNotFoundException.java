package com.winterwell.es.fail;

import com.winterwell.es.ESPath;
import com.winterwell.web.WebEx;

public class DocNotFoundException extends WebEx.E404 implements IElasticException {

	public DocNotFoundException(ESPath esPath) {
		super(null, "No doc "+esPath);
	}

	private static final long serialVersionUID = 1L;

}
