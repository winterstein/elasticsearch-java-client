package com.winterwell.es.client;

import java.io.File;

import com.winterwell.es.ESUtils;
import com.winterwell.gson.FlexiGson;
import com.winterwell.gson.Gson;
import com.winterwell.gson.GsonBuilder;
import com.winterwell.utils.Dep;
import com.winterwell.utils.StrUtils;
import com.winterwell.utils.io.FileUtils;
import com.winterwell.utils.io.Option;
import com.winterwell.utils.log.Log;

public class ESConfig {

	public static final String CLIENT_VERSION = "0.9";
	public static final String ES_SUPPORTED_VERSION = "5.0";
	
	@Override
	public String toString() {
		return "ESConfig [port=" + port + ", server=" + server + "]";
	}

	public ESConfig() {
	}
	
	public ESConfig sniffPort() {
		// Since we use a non-standard port as a bit of extra security
		// -- let's try to sniff out the port number
		try {
			String yml = FileUtils.read(new File("/etc/elasticsearch/elasticsearch.yml"));
			String[] lines = StrUtils.splitLines(yml);
			for (String line : lines) {
				line = line.trim();
				if (line.startsWith("http.port:")) {
					String sport = line.substring("http.port:".length()).trim();
					this.port = Integer.parseInt(sport);
					break;
				}
			}
		} catch(Throwable ex) {
			// oh well
			Log.d("ES", "Could not sniff port: "+ex);
		}
		return this;
	}
	
	/**
	 * The normal default is 9200. This can be changed in /etc/elasticsearch/elasticsearch.yml
	 */
	@Option
	public int port = 9200;
	
	@Option
	public String server = "localhost";

	private Gson gson;
	
	@Option
	private String indexAliasVersion = "1";
	
	public Gson getGson() {
		if (gson==null) gson = Dep.has(Gson.class)? Dep.get(Gson.class) : new Gson(); 
		return gson;
	}
	
	public ESConfig setGson(Gson gson) {
		this.gson = gson;		
		// ??could we put in a type adapter
		return this;
	}

	/**
	 * Convenience hack: It's handy to make an index with a version name (e.g. "foo_1")
	 * and an alias with a public name (e.g. "foo"). This provides a convenient place
	 * to set which "version" to use for new indices.
	 * It is not directly used in the ES client itself, though it is loaded from the
	 * .properties file.
	 */
	public String getIndexAliasVersion() {
		return indexAliasVersion;
	}
	public void setIndexAliasVersion(String indexAliasVersion) {
		this.indexAliasVersion = indexAliasVersion;
	}
	
}
