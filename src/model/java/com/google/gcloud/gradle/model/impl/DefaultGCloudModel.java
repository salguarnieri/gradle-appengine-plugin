package com.google.gcloud.gradle.model.impl;

import com.google.gcloud.gradle.model.GCloudCommand;
import com.google.gcloud.gradle.model.GCloudModel;

import java.io.Serializable;
import java.util.List;

public class DefaultGCloudModel implements GCloudModel, Serializable {
    final List<GCloudCommand> runConfigs;
    final List<GCloudCommand> deployConfigs;
    final String gcloudHome;
    final String appengineHome;
    final String project;
    final String verbosity;

    public DefaultGCloudModel(List<GCloudCommand> runConfigs, List<GCloudCommand> deployConfigs, String gcloudHome, String appengineHome, String project, String verbosity) {
        this.runConfigs = runConfigs;
        this.deployConfigs = deployConfigs;
        this.gcloudHome = gcloudHome;
        this.appengineHome = appengineHome;
        this.project = project;
        this.verbosity = verbosity;
    }

    @Override
    public List<GCloudCommand> getRunConfigs() {
        return runConfigs;
    }

    @Override
    public List<GCloudCommand> getDeployConfigs() {
        return deployConfigs;
    }

    @Override
    public String getGCloudHome() {
        return gcloudHome;
    }

    @Override
    public String getAppEngineHome() {
        return appengineHome;
    }

    @Override
    public String getProject() {
        return project;
    }

    @Override
    public String getVerbosity() {
        return verbosity;
    }
}
