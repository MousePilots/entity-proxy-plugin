/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mousepilots.entity.proxies.generator;

import java.io.File;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Cleans the {@link #generateDirectory}
 *
 * @goal deleteProxies
 * @phase pre-clean
 * @requiresDependencyResolution runtime
 * @author <a href="mailto:jurjenvangeenen@gmail.com">Jurjen van Geenen</a>
 * @version $Id$
 */
public class CleaningLady extends AbstractMojo {

    /**
     * Fully qualified packakge name to generate source files in - this plugin
     * MUST have this package and all its sub-packages to itself
     *
     * @parameter
     * @required
     */
    private String packageName;
    /**
     * Folder where generated-source will be created (automatically added to
     * compile classpath).
     *
     * @parameter
     * default-value="${project.build.directory}/generated-sources/entity-proxies"
     * @required
     */
    private File generateDirectory;

    public void execute() throws MojoExecutionException, MojoFailureException {
        clean(generateDirectory);
    }

    private void clean(File directory) {
        if (generateDirectory != null && generateDirectory.exists()) {
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    clean(file);
                }
                file.delete();
            }
        }
    }
}
