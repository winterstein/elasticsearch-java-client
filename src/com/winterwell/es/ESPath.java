package com.winterwell.es;

import java.util.Arrays;
import java.util.Collection;

import com.winterwell.utils.StrUtils;

/**
 * Wrap the 3 parts of an ES identifier (index, type, id) into one object
 * @author daniel
 *
 */
public final class ESPath<T> {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Arrays.hashCode(indices);
//		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		} else if ( ! id.equals(other.id))
			return false;
		if ( ! Arrays.equals(indices, other.indices))
			return false;
		return true;
	}

	public final String id;
	
	/**
	 * @deprecated types are gone in ESv7
	 */
	public final String type;
	
	public final String[] indices;
	
	public ESPath(String index, CharSequence id) {
		this(new String[]{index},null,id);
	}
	public ESPath(String[] indices, CharSequence id) {
		this(indices, null, id);
	}
	
	/**
	 * @deprecated go typeless
	 * @param indices
	 * @param type
	 * @param id
	 */
	public ESPath(String[] indices, String type, CharSequence id) {
		this.id = id==null? null : id.toString();
		this.type = type;
		this.indices = indices;		
	}
	
	/**
	 * @deprecated go typeless
	 * @param indices
	 * @param type
	 * @param id
	 */
	public ESPath(Collection<String> indices, String type, CharSequence id) {
		this(indices.toArray(StrUtils.ARRAY), type, id);		
	}
	
	/**
	 * @deprecated go typeless
	 * @param index
	 * @param type
	 * @param id
	 */
	public ESPath(String index, String type, CharSequence id) {
		this(new String[] {index}, type, id);
	}

	@Override
	public String toString() {
		return "ESPath[" +Arrays.toString(indices)+" / id: " +id+ "]";
	}

	public String index() {
		assert indices.length == 1 : this;
		return indices[0];
	}

	
}
