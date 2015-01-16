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
package com.google.gcloud.tooling

import com.google.gcloud.GCloudPluginExtension
import com.google.gcloud.exec.command.CommandBuilder
import com.google.gcloud.gradle.model.GCloudCommand
import com.google.gcloud.gradle.model.GCloudModel
import com.google.gcloud.gradle.model.impl.DefaultGCloudCommand
import com.google.gcloud.gradle.model.impl.DefaultGCloudModel
import com.google.gcloud.task.app.DeployTask
import com.google.gcloud.task.app.RunTask
import org.gradle.api.Incubating
import org.gradle.api.Project
import org.gradle.tooling.provider.model.ToolingModelBuilder

/**
 * AppEngine implementation of ToolingModelBuilder, populates the AppEngineModel
 */
@Incubating
public class GCloudToolingBuilderModel implements ToolingModelBuilder {
    @Override
    public boolean canBuild(String modelName) {
        return modelName.equals(GCloudModel.class.getName());
    }

    @Override
    public Object buildAll(String modelName, Project project) {

        List<GCloudCommand> runConfigs = project.tasks.withType(RunTask).collect { RunTask task ->
            CommandBuilder commandBuilder = task.getCommandBuilder();
            new DefaultGCloudCommand(commandBuilder.command*.toString() as String[],
                    commandBuilder.options*.toString() as String[],
                    commandBuilder.arguments*.toString() as String[])
        }
        List<GCloudCommand> deployConfigs = project.tasks.withType(DeployTask).collect { DeployTask task ->
            CommandBuilder commandBuilder = task.getCommandBuilder();
            new DefaultGCloudCommand(commandBuilder.command*.toString() as String[],
                    commandBuilder.options*.toString() as String[],
                    commandBuilder.arguments*.toString() as String[])
        }

        GCloudPluginExtension gcEx = project.extensions.getByName("gcloud")
        // TODO, this stuff is far from complete
        String gcloudHome = gcEx.gcloudHome  //or find it in path if not set?
        String appengineHome = "derive from gcloud home somehow" // there shoudl be a fixed area for this value
        String gcloudProject = gcEx.project
        String verbosity = gcEx.verbosity

        return new DefaultGCloudModel(runConfigs, deployConfigs, gcloudHome, appengineHome, gcloudProject, verbosity);
    }
}
