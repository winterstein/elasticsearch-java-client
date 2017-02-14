package com.winterwell.es.client;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.winterwell.es.ESUtils;
import com.winterwell.utils.StrUtils;
import com.winterwell.utils.io.FileUtils;
import com.winterwell.utils.io.Option;
import com.winterwell.utils.log.Log;

public class ESConfig {

	public static final String CLIENT_VERSION = "0.8";
	public static final String ES_SUPPORTED_VERSION = "1.2";
	
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

	// TODO expose gson setup options here??
	public Gson gson = ESUtils.gson();
	
}
