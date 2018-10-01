package com.winterwell.es.client.sort;

import java.util.Map;

import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.web.IHasJson;

/**
 * 
 * https://www.elastic.co/guide/en/elasticsearch/reference/6.4/search-request-sort.html
 * @author daniel
 *
 */
public class Sort implements IHasJson {

	public static Sort forField(String field, KSortOrder order) {
		return new Sort().setField(field).setOrder(order);
	}
	
	String field;
	
	KSortOrder order;
	
	public Sort setField(String field) {
		this.field = field;
		return this;
	}
	
	public Sort setOrder(KSortOrder order) {
		this.order = order;
		return this;
	}
	
	@Override
	public Map toJson2() throws UnsupportedOperationException {
		return new ArrayMap(
			field, new ArrayMap(
				"order", order
					)
		);
	}

}
