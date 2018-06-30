package com.winterwell.es.client.agg;

import java.util.Map;

import com.winterwell.utils.Utils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.web.IHasJson;

/**
 * Made via Aggregations
 * @author daniel
 *
 */
public class Aggregation implements IHasJson {

	
	
	@Override
	public String toString() {
		return "Aggregation [name=" + name + ", field=" + getField() + ", type=" + type + ", props=" + props + "]";
	}

	private String getField() {
		return (String) props.get("field");
	}

	public final String name;
	private String type;
	private final ArrayMap props;
	
	/**
	 * The missing parameter defines how documents that are missing a value should be treated. 
	 * By default they will be ignored. Setting this treats them as if they had a value.
	 * 
	 * see e.g. https://www.elastic.co/guide/en/elasticsearch/reference/6.1/search-aggregations-metrics-stats-aggregation.html#_missing_value_8
	 * or https://www.elastic.co/guide/en/elasticsearch/reference/6.1/search-aggregations-bucket-terms-aggregation.html#_missing_value_12
	 * 
	 * @param missing
	 * @return
	 */
	public Aggregation setMissing(Object missing) {
		props.put("missing", missing);

		// safety check on type: string in stats is an error
		if (missing != null) {
			if ("stats".equals(getField())) assert missing instanceof Number;
		}
			
		return this;
	}
	
	/**
	 * See {@link Aggregations}
	 * @param aggResultName
	 * @param aggType
	 * @param field
	 */
	Aggregation(String aggResultName, String aggType, String field) {
		Utils.check4null(aggResultName, aggType, field);
		this.name = aggResultName;
		this.type = aggType;
		this.props = new ArrayMap("field", field);
	}

	Map aggs;
	/**
	 * a weak defence against lifecycle-breaking edits -- but this does not protect against sub-aggs being edited!
	 */
	private transient boolean toJsond;
	
	/**
	 * {@inheritDoc}
	 * 
	 * This does NOT include the name, which is used by the parent search
	 */
	@Override
	public Map toJson2() throws UnsupportedOperationException {
		ArrayMap map = new ArrayMap(type, props);
		if (aggs!=null) {
			map.put("aggs", aggs);
		}
		toJsond = true;
		return map;
	}

	public Aggregation put(String k, Object v) {
		assert ! toJsond : "Cannot modify with "+k+"="+v+". This has already been converted into json :(";
		props.put(k, v);
		return this;
	}

	public Aggregation subAggregation(Aggregation dh) {
		assert ! toJsond : "Cannot modify with sub-agg "+dh+". This has already been converted into json :(";
		if (aggs==null) {
			aggs = new ArrayMap();
		}
		aggs.put(dh.name, dh); //.toJson2());
		return this;		
	}

	/**
	 * How many top terms to collect?
	 * See https://www.elastic.co/guide/en/elasticsearch/reference/current/search-aggregations-bucket-terms-aggregation.html
	 * @param numTerms
	 */
	public void setSize(int numTerms) {
		if ( ! "terms".equals(type)) throw new IllegalStateException("Wrong type of agg: "+this);
		put("size", numTerms);
	}

}
