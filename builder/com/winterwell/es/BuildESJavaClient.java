
package com.winterwell.es;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.winterwell.bob.BuildTask;
import com.winterwell.bob.tasks.MavenDependencyTask;
import com.winterwell.bob.wwjobs.BuildFlexiGson;
import com.winterwell.bob.wwjobs.BuildUtils;
import com.winterwell.bob.wwjobs.BuildWeb;
import com.winterwell.bob.wwjobs.BuildWinterwellProject;
import com.winterwell.utils.io.FileUtils;

public class BuildESJavaClient extends BuildWinterwellProject {

	public BuildESJavaClient() {
		super(new File(FileUtils.getWinterwellDir(), "elasticsearch-java-client"));
		setIncSrc(true);
		setScpToWW(true);
	}

	@Override
	public List<BuildTask> getDependencies() {
		List<BuildTask> deps = new ArrayList(super.getDependencies());
		
		MavenDependencyTask mdt = new MavenDependencyTask();
		mdt.addDependency("com.google.guava", "guava", "28.1-jre");
		mdt.setIncSrc(true);
		// This broke the logger (and hence everything 'cos Log4J is like that)
//<<<<<<< HEAD
//		mdt.setIncSrc(true);
//		mdt.addDependency("org.elasticsearch", "elasticsearch", "5.6.16");
//		// needed by ES -- how is this not added by ES itself??
//		// https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/_log4j_2_logger.html
//		mdt.addDependency("org.apache.logging.log4j", "log4j-core", "2.11.1");
//		
//		deps.add(0, mdt);		
//=======
		mdt.addDependency("org.elasticsearch", "elasticsearch", "5.3.3"); // TODO upgrade ES
		// needed by ES -- how is this not added by ES itself??
		// https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/_log4j_2_logger.html
		mdt.addDependency("org.apache.logging.log4j", "log4j-core", "2.11.1");		
		deps.add(0, mdt); // Off!
		// WW projects
		deps.add(new BuildUtils());
		deps.add(new BuildWeb());
		deps.add(new BuildFlexiGson());			
		return deps;
	}
	
	@Override
	public void doTask() throws Exception {
		super.doTask();		
//		File zflibs = new File(FileUtils.getWinterwellDir(), "zonefox/libs");
//		FileUtils.copy(getJar(), zflibs);
	}

}
