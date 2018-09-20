package com.winterwell.es.query;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.common.xcontent.XContentBuilder;


/**
 * Directly make the query input. This is useful for "manually" bridging between ES versions.
 * @author daniel
 */
public class MapQueryBuilder 
extends org.elasticsearch.index.query.QueryBuilder {

	final Map map;
	
	public Map getMap() {
		return map;
	}
	
	public MapQueryBuilder(Map map) {
		this.map = map;
	}
	
	@Override
	protected void doXContent(XContentBuilder builder, Params params) throws IOException {
		for(Object k : map.keySet()) {
			builder.field(k.toString(), map.get(k));
		}
	}

}
