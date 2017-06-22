package com.winterwell.es.client.agg;

import java.util.Map;

import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.web.IHasJson;

public class Aggregation implements IHasJson {

	@Override
	public String toString() {
		return name+": "+toJSONString();
	}
	
	public final String name;
	private String field;
	private String type;
	private final ArrayMap props;
	
	public Aggregation(String aggResultName, String aggType, String field) {
		Utils.check4null(aggResultName, aggType, field);
		this.name = aggResultName;
		this.type = aggType;
		this.field = field;
		this.props = new ArrayMap("field", field);
	}

	Map aggs;
	
	@Override
	public Map toJson2() throws UnsupportedOperationException {
		ArrayMap map = new ArrayMap(type, props);
		if (aggs!=null) {
			map.put("aggs", aggs);
		}
		return map;
	}

	public Aggregation put(String k, Object v) {
		props.put(k, v);
		return this;
	}

	public Aggregation subAggregation(Aggregation dh) {
		if (aggs==null) {
			aggs = new ArrayMap();
		}
		aggs.put(dh.name, dh.toJson2());
		return this;		
	}

}
