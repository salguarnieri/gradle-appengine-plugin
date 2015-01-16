package com.google.gcloud.gradle.model.impl;

import com.google.gcloud.gradle.model.GCloudCommand;

import java.io.Serializable;

/**
 * Created by appu on 2/13/15.
 */
public class DefaultGCloudCommand implements GCloudCommand, Serializable {
    final String[] command;
    final String[] options;
    final String[] arguments;

    public DefaultGCloudCommand(String[] command, String[] options, String[] arguments) {
        this.command = command;
        this.options = options;
        this.arguments = arguments;
    }

    @Override
    public String[] getCommand() {
        return command;
    }

    @Override
    public String[] getOptions() {
        return options;
    }

    @Override
    public String[] getArguments() {
        return arguments;
    }
}
