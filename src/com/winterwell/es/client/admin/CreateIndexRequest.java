package com.winterwell.es.client.admin;

import java.util.Map;

import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.es.client.IESResponse;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.web.SimpleJson;

public class CreateIndexRequest extends ESHttpRequest<CreateIndexRequest,IESResponse> {

	public CreateIndexRequest(ESHttpClient hClient, String index) {
		super(hClient);
		setIndex(index);
//		endpoint; Just do a put to the index url
		method = "PUT";
	}

	/**
	 * 
	 * @param analyzer e.g. keyword See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/analysis-analyzers.html
	 * @return 
	 * @see Analyzer
	 */
	public CreateIndexRequest setDefaultAnalyzer(String analyzer) {
		SimpleJson.set(body(), analyzer, 
				"index", "analysis", "analyzer", "default", "type");
		return this;
	}

	/**
	 * String constants for the built-in analyzer types.
	 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/analysis-analyzers.html
	 */
	public static final Analyzer Analyzer = new Analyzer();

	/**
	 * String constants for the built-in analyzer types.
	 * See http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/analysis-analyzers.html
	 */
	public static final class Analyzer {
		private Analyzer() {}
		/**
		The standard analyzer divides text into terms on word boundaries, as defined by the Unicode Text Segmentation algorithm. It removes most punctuation, lowercases terms, and supports removing stop words.
		 */
		public static final String standard = "standard";
		
//		Simple Analyzer
//		The simple analyzer divides text into terms whenever it encounters a character which is not a letter. It lowercases all terms.
//		public static final String 
		
		/** The whitespace analyzer divides text into terms whenever it encounters any whitespace character. It does not lowercase terms. */
		public static final String whitespace = "whitespace";
		
//		Stop Analyzer
//		The stop analyzer is like the simple analyzer, but also supports removal of stop words.
//		public static final String 
		
		/** The keyword analyzer is a “noop” analyzer that accepts whatever text it is given and outputs the exact same text as a single term. */
		public static final String keyword = "keyword";
		
//		Pattern Analyzer
//		The pattern analyzer uses a regular expression to split the text into terms. It supports lower-casing and stop words.
//		public static final String 
		
//		Language Analyzers
//		Elasticsearch provides many language-specific analyzers like english or french.
//		public static final String 
//		public static final String 
		
//		Fingerprint Analyzer
//		The fingerprint analyzer is a specialist analyzer which creates a fingerprint which can be used for duplicate detection.
//		public static final String 
	} // ./Analyzer
	
}
