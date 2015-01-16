package com.google.gcloud.exec.command

class CommandBuilder {
    List<String> command = []
    List<String> options = []
    List<String> arguments = []

    public CommandBuilder(String... parts) {
        Collections.addAll(command, parts);
    }

    /**
     * Returns a command String[] of the form command + options + arguments
     */
    public String[] buildCommand() {
        if (command.size() == 0) {
            throw new RuntimeException("No Command Specified");
        }
        if (!command.get(0).equals("gcloud")) {
            command.add(0, "gcloud");
        }
        List<String> commandLine = []
        commandLine.addAll(command)
        commandLine.addAll(options)
        commandLine.addAll(arguments)

        println command
        println options
        println arguments

        println commandLine

        return commandLine.toArray(new String[commandLine.size()])
    }

    public CommandBuilder addCommand(String... parts) {
        command.addAll(parts)
        return this;
    }

    /**
     * Convenience method to add non-null values as options to a gcloud command
     * in the format "--optionName=value" or "--optionName", does NOT escape special characters
     */
    public CommandBuilder addOption(String optionName, String value) {
        if (optionName == null || optionName.trim().isEmpty()) {
            throw new IllegalArgumentException("Option is null or empty")
        }
        if (value == null || value.trim().isEmpty()) {
            if (optionName.startsWith("--")) {
                options.add(optionName)
            }
            else {
                options.add("--" + optionName)
            }
            return this
        }
        if (optionName.startsWith("--")) {
            options.add(optionName + "=" + value)
        }
        else {
            options.add("--" + optionName + "=" + value)
        }
        return this
    }

    public CommandBuilder addOption(String option) {
        if (option == null || option.trim().isEmpty()) {
            throw new IllegalArgumentException("Option is null or empty")
        }
        if (option.startsWith("--")) {
            options.add(option)
        }
        else {
            options.add("--" + option)
        }
        return this
    }

    /**
     * Convenience method to add an argument, does NOT escape special characters,
     * arguments are inserted at the END of the commandline
     */
    public CommandBuilder addArgument(String argument) {
        arguments.add(argument)
        return this
    }

}
