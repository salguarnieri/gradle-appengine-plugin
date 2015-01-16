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
package com.google.gcloud.task

import com.google.gcloud.GCloudPluginExtension
import com.google.gcloud.exec.SynchronousProcessRunner
import com.google.gcloud.exec.command.CommandBuilder
import com.google.gcloud.exec.io.WatchableInputStreamConsumer
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Incubating
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.TaskAction

/**
 * GCloud raw task is never exposed, but users can override it if they so please
 */
@Incubating
class GCloudTask extends DefaultTask {

    final CommandBuilder commandBuilder = new CommandBuilder()

    // inputs
    List<String> command
    List<String> options

    LogLevel logLevel = LogLevel.LIFECYCLE

    public GCloudTask() {
        project.afterEvaluate {
            configureCommand()
            configureOptions()
        }
    }

    @TaskAction
    protected void start() {
        validateCloudSdk()
        executeTask()
    }

    void validateCloudSdk() {
        // TODO: validate the cloud sdk install somehow here
    }

    protected void configureCommand() {
        println "command ${getCommand()}"
        getCommand()?.each { part ->
            commandBuilder.addCommand(part)
        }
    }

    protected void configureOptions() {
        options?.each { option ->
            commandBuilder.addOption(option)
        }

        GCloudPluginExtension gcEx = project.extensions.getByType(GCloudPluginExtension)
        if (gcEx.project) {
            if (!commandBuilder.options.find { it.startsWith("--project=") } &&
                !commandBuilder.command.find { it.startsWith("--project=") }) {
                commandBuilder.addOption("project", gcEx.project)
            }
        }
        if (gcEx.verbosity) {
            if (!commandBuilder.options.find { it.startsWith("--verbosity=") } &&
                !commandBuilder.command.find { it.startsWith("--verbosity=") }) {
                commandBuilder.addOption("verbosity", gcEx.verbosity)
            }
        }
    }

    protected void executeTask() {
        SynchronousProcessRunner pr = new SynchronousProcessRunner(getCommandLine(), new WatchableInputStreamConsumer(project.logger, logLevel))
        int exitCode = pr.run()
        if (exitCode != 0) {
            throw new GradleException("gcloud failed with code " + exitCode)
        }
    }

    protected String[] getCommandLine() {
        commandBuilder.buildCommand()
    }
}
