package com.winterwell.es;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.winterwell.bob.BuildTask;
import com.winterwell.bob.tasks.EclipseClasspath;
import com.winterwell.utils.Printer;
import com.winterwell.utils.containers.Containers;
import com.winterwell.utils.containers.Tree;
import com.winterwell.utils.io.FileUtils;
import com.winterwell.utils.web.WebUtils;
import com.winterwell.utils.web.WebUtils2;
import com.winterwell.utils.web.XMLNode;

/**
 * TODO!
 * 
 * Edit your .classpath file to sync with the contents of a directory
 * @author daniel
 * @testedby UpdateEclipseClasspathTaskTest
 */
public class UpdateEclipseClasspathTask extends BuildTask {

	private File syncDir;
	private EclipseClasspath eclipseClasspath;

	public UpdateEclipseClasspathTask(EclipseClasspath eclipseClasspath, File syncDir) {
		this.eclipseClasspath = eclipseClasspath;
		this.syncDir = syncDir;
	}

	@Override
	protected void doTask() throws Exception {
		List<File> jars = eclipseClasspath.getReferencedLibraries();
		List<File> _depJars = Arrays.asList(syncDir.listFiles(
				f -> f.getName().endsWith(".jar") && ! f.getName().contains("-sources")
				));
		// TODO
		// what to remove?
		Collection<File> rm = new HashSet(jars);
		rm.removeAll(_depJars);
		// ...only remove if meant to be in syncDir
		rm = Containers.filter(rm, f -> f.getAbsolutePath().startsWith(syncDir.getAbsolutePath()));
		// what to add
		HashSet<File> add = new HashSet(_depJars);
		add.removeAll(jars);

		if (rm.isEmpty() && add.isEmpty() && false) {			
			// all good :)
			return;
		}
		// xml surgery
		String ec = FileUtils.read(eclipseClasspath.getFile());
		Tree<XMLNode> ecTree = WebUtils.parseXmlToTree(ec);
		Document ecDoc = WebUtils.parseXml(ec);
		//<classpathentry kind="lib" path="dependencies/checker-qual.jar"/>
		List<Node> nodes = WebUtils.xpathQuery2("//classpathentry[path=\"dependencies/checker-qual.jar\"]", ecDoc, false);
		if (nodes.size()!=0) {
			nodes.get(0).getParentNode().removeChild(nodes.get(0)); // xpath doesnt work??
		}
		Tree<XMLNode> classpathNode = null;
		for (Tree<XMLNode> node : ecTree) {			
			XMLNode v = node.getValue();
			if (v==null) continue;
			if ("classpath".equals(v.getTag())) {
				classpathNode = node; // for adds later
			}
			String path = v.getAttribute("path");
			if (path==null || ! "classpathentry".equals(v.getTag())) continue;
			// remove?
			for(File r : rm) {
				// absolute or relative path??
				// TODO eclipse project resolver might be nice here, but also painful
				if (path.equals(r.getPath())) {
					node.setParent(null);
					break;
				}
				try {
					String relf = FileUtils.getRelativePath(r, eclipseClasspath.getProjectDir());
					if (path.equals(relf)) {
						node.setParent(null);
						break;
					}
				} catch (IllegalArgumentException ex) {
					// nope
				}
			}
		}
		//add
		assert classpathNode != null;
		XMLNode newnode = new XMLNode("classpathentry");
		newnode.getAttributes().put("path", "dependencies/foo.jar");
		newnode.getAttributes().put("kind", "lib");
		new Tree(newnode).setParent(classpathNode);
				
//		String xml2 = WebUtils2.xmlDocToString(ecDoc);		
//		String xml2b = ecTree.fl
		Printer.out(rm, add, ec, nodes);
	}

	public void setSyncDir(File depdir) {
		syncDir = depdir;
	}

}
