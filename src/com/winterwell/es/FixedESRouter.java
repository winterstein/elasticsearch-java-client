package com.winterwell.es;

/**
 * Simple routing to a fixed index and type.
 * @author daniel
 */
public class FixedESRouter implements IESRouter {

	final String index;
	final String type;
	
	public FixedESRouter(String index, String type) {
		this.index = index;
		this.type = type;
	}

	@Override
	public ESPath getPath(String dataspaceIsIgnored, Class typeIsIgnored, String id, Object statusIsIgnored) {
		return new ESPath(index, type, id);
	}

}
