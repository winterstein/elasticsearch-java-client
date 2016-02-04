package com.winterwell.es.client;

import java.util.ArrayList;
import java.util.Map;

import com.winterwell.utils.containers.ArrayMap;

/**
 * One request can do multiple add/remove operations -- useful for flipping an alias.
 * 
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/indices-aliases.html
 * 
 * @see org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest
 * @author daniel
 * @testedby {@link IndicesAliasesRequestTest}
 */
public class IndicesAliasesRequest extends ESHttpRequest<IndicesAliasesRequest, IESResponse> {

	private ArrayList<Map> actions;

	public IndicesAliasesRequest(IndicesAdminClient iac) {
		super(iac.hClient);
		endpoint = "_aliases";
		method = "POST";
		actions = new ArrayList();
		src = new ArrayMap("actions", actions);
	}
	
	/**
	 * 
	 * @param index The underlying index, e.g. my_index_v1
	 * @param alias The public alias, e.g. my_index
	 */
	public IndicesAliasesRequest addAlias(String index, String alias) {
		return addRemoveAlias("add", index, alias);
	}
	
	/**
	 * 
	 * @param index The underlying index, e.g. my_index_v1
	 * @param alias The public alias, e.g. my_index
	 */
	public IndicesAliasesRequest removeAlias(String index, String alias) {
		return addRemoveAlias("remove", index, alias);
	}
	
	
	IndicesAliasesRequest addRemoveAlias(String op, String index, String alias) {
		actions.add(new ArrayMap(op, new ArrayMap("index", index, "alias", alias)));
		return this;
	}

}
