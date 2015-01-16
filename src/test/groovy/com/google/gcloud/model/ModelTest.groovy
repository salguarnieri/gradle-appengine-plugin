package com.google.gcloud.model

import com.google.appengine.AppIntegrationTest
import com.google.appengine.gradle.model.AppEngineModel
import com.google.gcloud.gradle.model.GCloudModel
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection
import org.junit.Assert
import org.junit.Test

/**
 * Smoke test to see that we can load in an App Engine Model
 */
class ModelTest extends AppIntegrationTest {

    public static final String TEST_APP = "gcloudModelTestApp"

    @Override
    protected String getTestAppName() {
        TEST_APP
    }

    @Test
    void modelLoadTest() {
        ProjectConnection connection = GradleConnector.newConnector().forProjectDirectory(projectRoot).connect()
        try {
            GCloudModel model = connection.getModel(GCloudModel.class)
            Assert.assertTrue(model.getRunConfigs().size() == 1)
            Assert.assertTrue(model.getDeployConfigs().size() == 2)
            Assert.assertTrue(model.getProject().equals("some-cloud-project"))
            Assert.assertTrue(model.getAppEngineHome().equals("derive from gcloud home somehow")) //TODO : fix this test
            Assert.assertTrue(model.getVerbosity().equals("info"))
            Assert.assertTrue(model.getGCloudHome() == null) //TODO : fix this test
        }

        finally {
            connection.close()
        }
    }
}
