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

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

/**
 * Created by appu on 12/10/14.
 */
class GCloudAppStopTask extends DefaultTask {
    int adminPort = 8000;
    String adminAddress = "localhost";

    @TaskAction
    protected void stopDevAppServer() {
        HttpURLConnection connection;
        URL url = new URL("http", adminAddress, adminPort, "/quit");
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))
            while(responseReader.readLine() != null);
            connection.disconnect();
            logger.info("Shutting down Cloud SDK Server on port " + 8000 + " and waiting 4 seconds...");
            Thread.sleep(4000);
        } catch (IOException e) {
            logger.debug("Was not able to contact the server to shut it down.  It may not be running anymore. ", e);
        } catch (InterruptedException e) {
            throw new GradleException("Interrupted when trying to shutdown devappserver", e);
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }
 }
