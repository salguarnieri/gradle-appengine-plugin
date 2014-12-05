package com.google.gcloud.wrapper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Simple gcloud command builder with helper functions
 */
public class GCloudCommandBuilder {

    ArrayList<String> command = new ArrayList<String>();

    public GCloudCommandBuilder() {
    }

    public GCloudCommandBuilder(String... parts) {
        Collections.addAll(command, parts);
    }

    /**
     * Returns a command String[] and adds gcloud if necessary
     */
    public String[] buildCommand() {
        if (command.size() == 0) {
            throw new RuntimeException("No Command Specified");
        }
        if (!command.get(0).equals("gcloud")) {
            command.add(0, "gcloud");
        }

        return command.toArray(new String[command.size()]);
    }

    public GCloudCommandBuilder add(String... parts) {
        Collections.addAll(command, parts);
        return this;
    }

    /**
     * Convenience method to add non-null values as options to a gcloud command
     * in the format "--optionName=value", does NOT escape special characters
     */
    public GCloudCommandBuilder addOption(String optionName, String value) {
        if (optionName == null || optionName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is null or empty");
        }
        if (optionName.startsWith("--")) {
            throw new IllegalArgumentException("Do not include -- in option name");
        }
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Value is null or empty");
        }
        command.add("--" + optionName + "=" + value);
        return this;
    }

    /**
     * Convenience method to add an option, does NOT escape special characters
     */
    public GCloudCommandBuilder addOption(String optionName) {
        if (optionName == null || optionName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is null or empty");
        }
        if (optionName.startsWith("--")) {
            throw new IllegalArgumentException("Do not include -- in option name");
        }
        command.add("--" + optionName);
        return this;
    }

}
