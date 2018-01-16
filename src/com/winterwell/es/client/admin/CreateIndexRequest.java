package com.winterwell.es.client.admin;

import java.util.Map;

import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.es.client.IESResponse;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.web.SimpleJson;

/**
 * Create a new index!
 * 
 * @author daniel
 * @testedby CreateIndexRequestTest
 */
public class CreateIndexRequest extends ESHttpRequest<CreateIndexRequest,IESResponse> {

	private boolean failIfAliasExists;

	public CreateIndexRequest(ESHttpClient hClient, String index) {
		super(hClient, null);
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
	 * TODO 
	 * https://www.elastic.co/guide/en/elasticsearch/reference/current/analysis-stemmer-tokenfilter.html
	 * 
	 */
	public CreateIndexRequest addTokenFilter(String analyzer) {
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
		
		/** Language Analyzers
			Elasticsearch provides many language-specific analyzers like english or french.
		See https://www.elastic.co/guide/en/elasticsearch/reference/current/analysis-lang-analyzer.html
		*/
		public static final String arabic = "arabic";
 public static final String armenian = "armenian";
 public static final String basque = "basque";
 public static final String brazilian = "brazilian";
 public static final String bulgarian = "bulgarian";
 public static final String catalan = "catalan";
 public static final String cjk = "cjk";
 public static final String czech = "czech";
 public static final String danish = "danish";
 public static final String dutch = "dutch";
 /** Language Analyzers
	Elasticsearch provides many language-specific analyzers like english or french.
See https://www.elastic.co/guide/en/elasticsearch/reference/current/analysis-lang-analyzer.html
*/
 public static final String english = "english";
 public static final String finnish = "finnish";
 public static final String french = "french";
 public static final String galician = "galician";
 public static final String german = "german";
 public static final String greek = "greek";
 public static final String hindi = "hindi";
 public static final String hungarian = "hungarian";
 public static final String indonesian = "indonesian";
 public static final String irish = "irish";
 public static final String italian = "italian";
 public static final String latvian = "latvian";
 public static final String lithuanian = "lithuanian";
 public static final String norwegian = "norwegian";
 public static final String persian = "persian";
 public static final String portuguese = "portuguese";
 public static final String romanian = "romanian";
 public static final String russian = "russian";
 public static final String sorani = "sorani";
 public static final String spanish = "spanish";
 public static final String swedish = "swedish";
 public static final String turkish = "turkish";
 public static final String thai = "thai";
//		public static final String 
		
//		Fingerprint Analyzer
//		The fingerprint analyzer is a specialist analyzer which creates a fingerprint which can be used for duplicate detection.
//		public static final String 
	} // ./Analyzer

	/**
	 * Also create an alias.
	 * 
	 * What happens if the alias already exists?
	 * ES will happily let you create multiple indices with the same alias! 
	 * BUT an index request (i.e. "put this doc into alias") will fail.
	 * 
	 * See https://www.elastic.co/guide/en/elasticsearch/reference/5.5/indices-create-index.html#create-index-aliases
	 * @param index
	 * @return 
	 */
	public CreateIndexRequest setAlias(String index) {
		body().put("aliases", new ArrayMap(index, new ArrayMap()));
		return this;
	}
	
	/**
	 * @deprecated Not implemented on this branch!
	 * 
	 * If set, test for a create-index-with-alias race.
	 * If this create call loses the race, then the response  result is an error.
	 * @param b
	 */
	public void setFailIfAliasExists(boolean b) {
		failIfAliasExists = b;
	}
	
}
