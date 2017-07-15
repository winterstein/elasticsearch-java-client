package com.winterwell.es;

import java.util.Arrays;

/**
 * Wrap the 3 parts of an ES identifier into one object
 * @author daniel
 *
 */
public class ESPath {

	public final String id;
	public final String type;
	public final String[] indices;
	
	public ESPath(String[] indices, String type, String id) {
		this.id = id;
		this.type = type;
		this.indices = indices;		
	}
	
	public ESPath(String index, String type, String id) {
		this(new String[] {index}, type, id);
	}

	@Override
	public String toString() {
		return "ESPath[" +Arrays.toString(indices)+" / " + type + " / " +id+ "]";
	}

	public String index() {
		assert indices.length == 1 : this;
		return indices[0];
	}

	
}
