/*
 * Copyright 2014 the original author or authors.
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
package com.google.gcloud.task

import com.google.appengine.AppEnginePlugin
import com.google.gcloud.GCloudPluginExtension
import com.google.gcloud.wrapper.GCloudCommandBuilder
import org.gradle.api.Incubating

/**
 * GCloud task is never exposed, but users can override it if they so please
 */
@Incubating
class GCloudTask extends AbstractGCloudTask {
    GCloudCommandBuilder commandBuilder

    GCloudTask() {
        commandBuilder = new GCloudCommandBuilder()
    }

    // must run in afterEvaluate
    protected void addCommonOpts() {
        GCloudPluginExtension gcEx = project.extensions.getByName("gcloud")
        if (gcEx.project) {
            opt("project", gcEx.project)
        }
    }

    protected void addAppDir() {
        commandBuilder.add(AppEnginePlugin.getExplodedAppDirectory(project).absolutePath)
    }

    @Override
    protected String[] getCommand() {
        return commandBuilder.buildCommand()
    }

    void opt(String name, String value) {
        commandBuilder.addOption(name, value)
    }

    void opt(String name) {
        commandBuilder.addOption(name)
    }

    void command(String[] command) {
        commandBuilder.add(command)
    }

}
