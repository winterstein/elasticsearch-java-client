package com.winterwell.es.client.admin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.winterwell.es.client.ESHttpClient;
import com.winterwell.es.client.ESHttpRequest;
import com.winterwell.es.client.ESHttpResponse;
import com.winterwell.es.client.IESResponse;
import com.winterwell.utils.containers.ArrayMap;
import com.winterwell.utils.containers.Containers;
import com.winterwell.utils.log.Log;
import com.winterwell.utils.web.SimpleJson;
import com.winterwell.web.WebEx;

/**
 * Create a new index!
 * https://www.elastic.co/guide/en/elasticsearch/reference/7.6/indices-create-index.html
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
	
	@Override
	protected ESHttpResponse doExecute(ESHttpClient esjc) {		
		// call the super method to do the main work
		ESHttpResponse r = super.doExecute(esjc);
		// fail?
		if ( ! failIfAliasExists) return r;
		// Before we check the alias, let's check the actual create call
		if ( ! r.isSuccess()) return r;
		Map aliases = (Map) body().get("aliases");
		if (aliases==null || aliases.isEmpty()) {
			Log.i("ES.CreateIndex", "failIfAliasExists was set, but no aliases were set");
			return r;
		}
		String alias = (String) Containers.only(aliases.keySet());
		// check this index
		IndexSettingsRequest req2 = esjc.admin().indices().indexSettings(alias);
		IESResponse resp = req2.get().check();
		Map<String,Object> settingsFromIndex = resp.getParsedJson();
		if (settingsFromIndex.size() == 1) return r;
		// Oh no! overlap -- was this the first in the race?
		String thisIndex = getIndices().get(0);
		long cd = Long.valueOf(SimpleJson.get(settingsFromIndex, thisIndex, "settings", "index", "creation_date"));		
		for(String idx : settingsFromIndex.keySet()) {
			if (idx.equals(thisIndex)) continue;
			long cd2 = Long.valueOf(SimpleJson.get(settingsFromIndex, idx, "settings", "index", "creation_date"));
			if (cd2 < cd) {
				// we lost :(
				WebEx.E40X error = new WebEx.E400("index-alias "+alias+" already exists");
				ESHttpResponse rfail = new ESHttpResponse(this, error);
				DeleteIndexRequest del = esjc.admin().indices().prepareDelete(thisIndex);
				del.setDebug(debug);
				del.execute();
				return rfail;
			}
		}
		// we won
		return r;
	}
	
	
	/**
	 * 
	 * @param analyzer e.g. keyword See 
	 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/analysis-analyzers.html
	 * @return 
	 * @see Analyzer
	 */
	public CreateIndexRequest setDefaultAnalyzer(String analyzer) {
		SimpleJson.set(body(), analyzer, 
				"settings", // NB: was "index" for ESv5 
				"analysis", "analyzer", "default", "type");
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
	 * @param alias Another name which can be used for this index. The alias if a proxy.
	 * @return 
	 */
	public CreateIndexRequest setAlias(String alias) {		
		return setAliases(Arrays.asList(alias));
	}
	
	public CreateIndexRequest setAliases(List<String> aliases) {
		ArrayMap as = new ArrayMap();
		for (String alias : aliases) {
			as.put(alias, new ArrayMap());
		}
		body().put("aliases", as);
		return this;
	}

	/**
	 * If set, test for a create-index-with-alias race.
	 * If this create call loses the race, then the response  result is an error.
	 * @param b
	 */
	public void setFailIfAliasExists(boolean b) {
		failIfAliasExists = b;
	}
	
}
