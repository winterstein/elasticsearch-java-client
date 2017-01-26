package com.winterwell.es.query;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.common.xcontent.ToXContent.Params;
import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.index.query.BaseFilterBuilder;
//import org.elasticsearch.index.query.BaseQueryBuilder;
import org.elasticsearch.index.query.BaseFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;

/**
 * Directly make the query input. This is useful for "manually" bridging between ES versions.
 * @author daniel
 */
public class MapFilterBuilder extends BaseFilterBuilder {
	
	final Map map;
	
	public MapFilterBuilder(Map map) {
		this.map = map;
	}
	
	@Override
	protected void doXContent(XContentBuilder builder, Params params) throws IOException {
		for(Object k : map.keySet()) {
			builder.field(k.toString(), map.get(k));
		}
	}

}
