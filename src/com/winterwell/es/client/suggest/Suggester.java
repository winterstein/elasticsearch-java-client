package com.winterwell.es.client.suggest;

import java.util.Map;

import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.web.IHasJson;

public class Suggester implements IHasJson {

	Map map = new ArrayMap();
	public String name;
	
	public Suggester(String name) {
		this.name = name;
	}

	@Override
	public Map toJson2() throws UnsupportedOperationException {
		return map;
	}

}
