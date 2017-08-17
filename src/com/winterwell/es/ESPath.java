package com.winterwell.es;

import java.util.Arrays;

/**
 * Wrap the 3 parts of an ES identifier (index, type, id) into one object
 * @author daniel
 *
 */
public final class ESPath {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(indices);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ESPath other = (ESPath) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (!Arrays.equals(indices, other.indices))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

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
