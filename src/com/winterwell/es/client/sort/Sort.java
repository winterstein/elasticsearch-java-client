package com.winterwell.es.client.sort;

import java.util.Map;

import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.web.IHasJson;

/**
 * 
 * https://www.elastic.co/guide/en/elasticsearch/reference/6.4/search-request-sort.html
 * @author daniel
 *
 */
public class Sort implements IHasJson {
	

	@Override
	public String toString() {
		return "Sort[" + field + " " + order + "]";
	}

	public Sort(String field, KSortOrder order) {
		this.field = field;
		this.order = order;
	}
	/**
	 * You MUST call setField() before using.
	 */
	public Sort() {
		
	}
	
	/**
	 * @deprecated Equivalent to new Sort() -- added for drop-in compatibility with SortBuilders 
	 * @param field
	 * @param order
	 * @return
	 */
	public static Sort fieldSort(String field, KSortOrder order) {
		return new Sort(field, order);
	}
	
	String field;
	
	KSortOrder order = KSortOrder.asc;

	private String missing;
	
	public Sort setField(String field) {
		this.field = field;
		assert ! Utils.isBlank(field);
		return this;
	}
	
	public Sort setOrder(KSortOrder order) {
		this.order = order;
		return this;
	}
	
	@Override
	public Object toJson2() {
		if (field==null) throw new IllegalStateException("Sort must be on a field");
		return new ArrayMap(
			field, new ArrayMap(
				"order", order,
				"missing", missing
					)
		);
	}

	public static Sort fieldSort(String field) {
		return fieldSort(field, null);
	}

	/**
	 * How to handle sort over missing values?
	 * https://www.elastic.co/guide/en/elasticsearch/reference/6.4/search-request-sort.html#_missing_values
	 * @param _last_first_or_customValue Note: "_last" is the default
	 * @return this
	 */
	public Sort setMissing(String _last_first_or_customValue) {
		this.missing = _last_first_or_customValue;
		return this;
	}

	public static Sort scoreSort() {
		return new ScoreSort();
	}	

}

/**
 * https://www.elastic.co/guide/en/elasticsearch/reference/6.4/search-request-sort.html#search-request-sort
 * @author daniel
 *
 */
class ScoreSort extends Sort {
	@Override
	public String toJson2() {
		return "_score";
	}
}
