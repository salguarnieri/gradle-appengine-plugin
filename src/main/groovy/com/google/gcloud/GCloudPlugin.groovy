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
package com.google.gcloud

import com.google.gcloud.task.GCloudAppDeployTask
import com.google.gcloud.task.GCloudAppRunTask
import com.google.gcloud.task.GCloudAppStopTask
import com.google.gcloud.task.GCloudTask
import org.gradle.api.Incubating
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * <p>A {@link Plugin} that provides tasks for generic use of gcloud in gradle and
 * for uploading and running of Google App Engine projects via gcloud.</p>
 *
 * @author Appu Goundan
 */
@Incubating
public class GCloudPlugin implements Plugin<Project> {

    static final String TASK_GCLOUD_APP_DEPLOY = "gcloudAppDeploy"
    static final String TASK_GCLOUD_APP_RUN = "gcloudAppRun"
    static final String TASK_GCLOUD_APP_STOP = "gcloudAppStop"

    @Override
    void apply(Project project) {

        project.apply plugin: "appengine"

        project.extensions.create('gcloud', GCloudPluginExtension, project)

        project.tasks.create(TASK_GCLOUD_APP_DEPLOY, GCloudAppDeployTask)
        project.tasks.create(TASK_GCLOUD_APP_RUN, GCloudAppRunTask)
        project.tasks.create(TASK_GCLOUD_APP_STOP, GCloudAppStopTask)
    }
}
