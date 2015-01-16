package com.google.gcloud.gradle.model;

import java.util.List;

public interface GCloudModel {

    public List<GCloudCommand> getRunConfigs();
    public List<GCloudCommand> getDeployConfigs();
    public String getGCloudHome();
    public String getAppEngineHome();
    public String getProject();
    public String getVerbosity();

}
