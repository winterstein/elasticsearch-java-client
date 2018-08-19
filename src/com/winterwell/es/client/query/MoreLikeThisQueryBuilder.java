package com.winterwell.es.client.query;

import java.util.Collection;
import java.util.Map;
import com.winterwell.utils.containers.ArrayMap;

/**
 * See https://www.elastic.co/guide/en/elasticsearch/reference/6.3/query-dsl-mlt-query.html
 * @author daniel
 *
 */
public class MoreLikeThisQueryBuilder extends ESQueryBuilder {

	public MoreLikeThisQueryBuilder() {
		this(new ArrayMap("more_like_this", new ArrayMap()));
	}
	
	public MoreLikeThisQueryBuilder(Map query) {
		super(query);
	}

	public MoreLikeThisQueryBuilder(String like) {
		this();
		props.put("like", like);
	}

	public MoreLikeThisQueryBuilder setFields(Collection<String> fields) {
		props.put("fields", fields);
		return this;
	}

	public MoreLikeThisQueryBuilder setMinTermFreq(int i) {
		props.put("min_term_freq", i);
		return this;
	}

	public MoreLikeThisQueryBuilder setMinDocFreq(int i) {
		props.put("min_doc_freq", i);
		return this;
	}
	
}
