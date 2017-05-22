package com.winterwell.es.query;

import java.io.IOException
;
import java.util.Map;

import org.apache.lucene.search.Query;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.QueryShardContext;

import com.winterwell.utils.TodoException;

/**
 * Directly make the query input. This is useful for "manually" bridging between ES versions.
 * @author daniel
 */
public class MapQueryBuilder extends AbstractQueryBuilder<MapQueryBuilder> 
implements org.elasticsearch.index.query.QueryBuilder {

	final Map map;
	
	public MapQueryBuilder(Map map) {
		this.map = map;
	}
	
	@Override
	protected void doXContent(XContentBuilder builder, Params params) throws IOException {
		for(Object k : map.keySet()) {
			builder.field(k.toString(), map.get(k));
		}
	}

	@Override
	public String getWriteableName() {
		// TODO Auto-generated method stub
		throw new TodoException();
	}

	@Override
	protected void doWriteTo(StreamOutput out) throws IOException {
		// TODO Auto-generated method stub
		throw new TodoException();
	}

	@Override
	protected Query doToQuery(QueryShardContext context) throws IOException {
		// TODO Auto-generated method stub
		throw new TodoException();
	}

	@Override
	protected boolean doEquals(MapQueryBuilder other) {
		return this == other;
	}

	@Override
	protected int doHashCode() {
		return this.hashCode();
	}

}
