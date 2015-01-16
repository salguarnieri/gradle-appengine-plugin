/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.gcloud.task.app

import com.google.appengine.AppEnginePlugin
import com.google.gcloud.task.GCloudTask
import org.gradle.api.Project

class AppTask extends GCloudTask {

    List<Project> modules
    List<File> warDirs

    public AppTask() {
        project.afterEvaluate {
            configureAppDirs()
        }
    }

    protected void configureAppDirs() {
        if (modules || warDirs) {
            modules.each { module ->
                commandBuilder.addArgument(AppEnginePlugin.getExplodedAppDirectory(module).absolutePath)
            }
            warDirs.each { warDir ->
                commandBuilder.addArgument(warDir.absolutePath)
            }
        }
        else {
            commandBuilder.addArgument(AppEnginePlugin.getExplodedAppDirectory(project).absolutePath)
        }
    }
}
