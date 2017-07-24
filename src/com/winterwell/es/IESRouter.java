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
	 * @return
	 */
	ESPath getPath(Class type, String id, Object status);
}
