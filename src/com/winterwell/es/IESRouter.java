package com.winterwell.es;


/**
 * Convenience for matching classes to indexes
 * @author daniel
 *
 */
public interface IESRouter {

	/**
	 * 
	 * @param type
	 * @param id
	 * @param status
	 * @param dataspace Optional name-spacing -- often this maps to an index
	 * @return
	 */
	<T> ESPath<T> getPath(CharSequence dataspace, Class<T> type, String id, Object status);
	
	default <T> ESPath<T> getPath(Class<T> type, String id) {
		return getPath(null, type, id, null);
	}
	
	default <T> ESPath<T> getPath(Class<T> type, String id, Object status) {
		return getPath(null, type, id, status);
	}
}
