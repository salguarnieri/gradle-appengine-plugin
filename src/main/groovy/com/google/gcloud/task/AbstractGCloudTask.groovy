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

import com.google.gcloud.GCloudPluginExtension
import com.google.gcloud.wrapper.GCloud
import com.google.gcloud.wrapper.impl.GCloudCommandLineWrapper
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Incubating
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * GCloud raw task is never exposed, but users can override it if they so please
 */
@Incubating
abstract class AbstractGCloudTask extends DefaultTask {
    boolean runAsync = false

    @TaskAction
    protected void executeTask() {
        GCloud gcloud = new GCloudCommandLineWrapper(getCommand())
        GCloudPluginExtension gcEx = project.extensions.getByName("gcloud")
        if (gcEx.gcloudHome) {
            gcloud.setGCloudHome(new File(gcEx.gcloudHome))
        }
        println(getCommand())
        if (runAsync) {
            gcloud.runAsync(null)
        }
        else {
            int exitCode = gcloud.runSync()
            if (exitCode != 0) {
                throw new GradleException("gcloud failed with code " + exitCode)
            }
        }
    }

    abstract protected String[] getCommand()

}
