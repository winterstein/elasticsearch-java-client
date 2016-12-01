package com.winterwell.es.client;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.winterwell.utils.TodoException;
import com.winterwell.utils.containers.ArrayMap;

/**
 * 
 * @author Daniel
 * @testedby {@link BulkRequestBuilderTest}
 */
public class BulkRequestBuilder extends ESHttpRequest<BulkRequestBuilder,BulkResponse> {

	public BulkRequestBuilder(ESHttpClient hClient) {
		super(hClient);
		endpoint = "_bulk";
		method = "POST";
	}

	List<ESHttpRequest> actions = new ArrayList();
	
	public BulkRequestBuilder add(ESHttpRequest request) {
		actions.add(request);		
		if (request.indices==null) {
			throw new IllegalArgumentException("No index set for "+request);
		}
		return this;
	}
		
	@Override
	public String getBodyJson() {
		String srcJson = "";
		for(ESHttpRequest req : actions) {
			String op = req.bulkOpName;
			if (op==null) throw new TodoException(req);
			ArrayMap opMap = new ArrayMap(
					"_index", req.indices[0], "_type", req.type, "_id", req.id
			);
			if ( ! req.params.isEmpty()) {
				opMap.putAll(req.params);
			}
			Map actionObj = new ArrayMap(op, opMap);
			srcJson += gson().toJson(actionObj).trim()+"\n";
			srcJson += req.getBodyJson().trim()+"\n";
		}
		return srcJson;
	}
	
}
