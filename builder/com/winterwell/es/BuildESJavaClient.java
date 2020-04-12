
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
		deps.add(mdt);
		
		// WW projects
		deps.add(new BuildUtils());
		deps.add(new BuildWeb());
		deps.add(new BuildFlexiGson());			
		return deps;
	}
	
	@Override
	public void doTask() throws Exception {
		super.doTask();		
	}

}
