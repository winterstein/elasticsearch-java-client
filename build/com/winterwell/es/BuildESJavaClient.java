
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

	public BuildESJavaClient() {
		super(new File(FileUtils.getWinterwellDir(), "elasticsearch-java-client"));
		setIncSrc(true);
	}

	@Override
	public void doTask() throws Exception {
		super.doTask();
	}

}
