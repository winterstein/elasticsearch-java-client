
package com.winterwell.es;

import java.io.File;
import java.util.Set;
import java.util.logging.Level;

import com.winterwell.bob.tasks.CopyTask;
import com.winterwell.bob.tasks.EclipseClasspath;
import com.winterwell.bob.tasks.JarTask;
import com.winterwell.bob.tasks.RSyncTask;
import com.winterwell.bob.tasks.SCPTask;

import com.winterwell.utils.io.FileUtils;

import jobs.BuildBob;
import jobs.BuildDepot;
import jobs.BuildMaths;
import jobs.BuildDataLog;
import jobs.BuildUtils;
import jobs.BuildWeb;
import jobs.BuildWinterwellProject;

public class BuildESJavaClient extends BuildWinterwellProject {

	private String ESVERSION = "2";

	public BuildESJavaClient() {
		super(
			FileUtils.getWorkingDirectory(), // FIXME how to handle two git checkouts in different directories??
			"elasticsearch-java-client"
//			new File(FileUtils.getWinterwellDir(), "elasticsearch-java-client")
			);
		setIncSrc(true);
		setVersion("esversion/"+ESVERSION); // this (the git branch name) will go into the manifest
		if (ESVERSION != null) {
			jarFile = new File(projectDir,  projectName+"-es"+ESVERSION+".jar");
		}
	}

	@Override
	public void doTask() throws Exception {
		super.doTask();		
//		File zflibs = new File(FileUtils.getWinterwellDir(), "zonefox/libs");
//		FileUtils.copy(getJar(), zflibs);
	}

}
