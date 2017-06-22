package com.winterwell.es.client.agg;

import java.util.Map;

public class AggregationResults {

	private String name;
	private Map results;

	public AggregationResults(String aggName, Map rs) {
		this.name = aggName;
		this.results = rs;
	}

	@Override
	public String toString() {
		return "AggregationResults[ "+name+": "+results+"]";
	}
}
