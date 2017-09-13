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
	ESPath getPath(String dataspace, Class type, String id, Object status);
	
	default ESPath getPath(Class type, String id) {
		return getPath(null, type, id, null);
	}
	
	default ESPath getPath(Class type, String id, Object status) {
		return getPath(null, type, id, status);
	}
}
