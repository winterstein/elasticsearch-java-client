
package com.winterwell.es;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.winterwell.bob.BuildTask;
import com.winterwell.bob.tasks.MavenDependencyTask;
import com.winterwell.utils.io.FileUtils;

import jobs.BuildWinterwellProject;

public class BuildESJavaClient extends BuildWinterwellProject {

	private String ESVERSION = "2";

	@Override
	public Collection<? extends BuildTask> getDependencies() {		
		List<BuildTask> deps = new ArrayList(super.getDependencies());		
		deps.add(new MavenDependencyTask("joda-time:joda-time:2.10"));
		return deps;
	}
	
	public BuildESJavaClient() {
		super(
			FileUtils.getWorkingDirectory(), // FIXME how to handle two git checkouts in different directories??
			"elasticsearch-java-client"
//			new File(FileUtils.getWinterwellDir(), "elasticsearch-java-client")
			);
		setIncSrc(true);
		setVersion("esversion/"+ESVERSION); // this (the git branch name) will go into the manifest
		if (ESVERSION != null) {
			setJar(new File(projectDir,  projectName+"-es"+ESVERSION+".jar"));
		}
	}

	@Override
	public void doTask() throws Exception {
		super.doTask();		
		
		File zflibs = new File(FileUtils.getWinterwellDir(), "zonefox/libs");
		File f = new File(zflibs, projectName+".jar");
		FileUtils.copy(getJar(), f);
	}

}
