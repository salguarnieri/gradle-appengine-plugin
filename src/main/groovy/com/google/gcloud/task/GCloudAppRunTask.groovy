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

import com.google.gcloud.wrapper.GCloudCommandBuilder
import org.gradle.api.Incubating

/**
 * Task to Run App Engine projects locally
 */
@Incubating
class GCloudAppRunTask extends GCloudTask {

    GCloudAppRunTask() {
        commandBuilder = new GCloudCommandBuilder("preview", "app", "run")
        project.afterEvaluate {
            // defer till everything is resolved (but before task execution)
            addCommonOpts()
            addAppDir()
        }
    }

    @Override
    protected String[] getCommand() {
        return super.getCommand();
    }

    @Override
    void command(String[] command) {
        throw new UnsupportedOperationException("Cannot assign command for App Run Task")
    }
}
