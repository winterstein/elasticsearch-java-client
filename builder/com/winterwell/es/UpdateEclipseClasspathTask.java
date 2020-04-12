package com.winterwell.es;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.winterwell.bob.BuildTask;
import com.winterwell.bob.tasks.EclipseClasspath;
import com.winterwell.utils.Printer;
import com.winterwell.utils.io.FileUtils;

public class UpdateEclipseClasspathTask extends BuildTask {

	private File syncDir;
	private EclipseClasspath eclipseClasspath;

	public UpdateEclipseClasspathTask(EclipseClasspath eclipseClasspath) {
		this.eclipseClasspath = eclipseClasspath;
	}

	@Override
	protected void doTask() throws Exception {
		List<File> jars = eclipseClasspath.getReferencedLibraries();
		List<File> _depJars = Arrays.asList(syncDir.listFiles(
				f -> f.getName().endsWith(".jar") && ! f.getName().contains("-sources")
				));
		// TODO
		// what to remove?
		HashSet<File> rm = new HashSet(jars);
		rm.removeAll(_depJars);
		// what to add
		HashSet<File> add = new HashSet(_depJars);
		add.removeAll(jars);
		String ec = FileUtils.read(eclipseClasspath.getFile());
		Printer.out(rm, add, ec);
	}

	public void setSyncDir(File depdir) {
		syncDir = depdir;
	}

}
