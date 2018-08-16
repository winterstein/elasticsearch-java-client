package com.winterwell.es.client.query;

import java.util.Map;

import org.elasticsearch.index.query.QueryBuilder;

import com.winterwell.es.ESUtils;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.containers.Containers;
import com.winterwell.utils.web.IHasJson;

/**
 * Base class for query-builders. For common cases use the convenience methods in {@link ESQueryBuilders}
 * @author daniel
 *
 */
public class ESQueryBuilder implements IHasJson, Cloneable {

	@Override
	public ESQueryBuilder clone() {
		ESQueryBuilder clone = new ESQueryBuilder(new ArrayMap(jobj));
		return clone;
	}
	
	@Override
	public String toString() {	
		return "ESQueryBuilder"+toJSONString();
	}
	
	Map jobj;
	protected transient boolean lock;
	/**
	 * Many queries have one top level key, and the sub-object is where the settings go 
	 */
	protected Map props;

	/**
	 * Direct access to the jobject map.
	 * @throws IllegalStateException see {@link #lockCheck()}
	 */
	public Map getUnderlyingMap() {
		lockCheck();
		return jobj;
	}
	
	/**
	 * 
	 * @param query Used directly! beware of side effects
	 */
	public ESQueryBuilder(Map query) {
		this.jobj = query;
		// Convenience hack
		if (query.size()==1) {
			this.props = (Map) Containers.first(jobj.values());	
		}
	}
	
	public ESQueryBuilder(QueryBuilder query) {
		this(ESUtils.jobj(query));
	}
	protected void lockCheck() throws IllegalStateException {
		if (lock) {
			throw new IllegalStateException("modified after toJson2() was used -- not allowed ('cos: risk of losing edits)");
		}
	}
	/**
	 * Construct an ESQueryBuilder from a QueryBuilder, ESQueryBuilder, or Map.
	 * 
	 * Note: the input object should not be modified afterwards!
	 *  
	 * @param query_mapOrQueryBuilder
	 * @return
	 */
	public static ESQueryBuilder make(Object query_mapOrQueryBuilder) {
		if (query_mapOrQueryBuilder instanceof ESQueryBuilder) {
			return (ESQueryBuilder) query_mapOrQueryBuilder;
		}
		if (query_mapOrQueryBuilder instanceof QueryBuilder) {
			return new ESQueryBuilder((QueryBuilder)query_mapOrQueryBuilder);
		}
		return new ESQueryBuilder((Map)query_mapOrQueryBuilder);
	}

	@Override
	public Map<String,Object> toJson2() throws UnsupportedOperationException {
		lock = true;
		return jobj;
	}
}
