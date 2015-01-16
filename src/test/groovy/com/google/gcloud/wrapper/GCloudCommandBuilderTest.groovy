package com.google.gcloud.wrapper

import com.google.gcloud.exec.command.CommandBuilder
import spock.lang.Specification

/**
 * Command Builder test
 */
class GCloudCommandBuilderTest extends Specification {
    CommandBuilder builder

    def setup() {
        builder = new CommandBuilder()
    }

    def "Test valid command builder"() {
        when:
        builder.addCommand("gcloud", "test1", "test2")
                .addOption("bool1")
                .addArgument("tomato")
                .addOption("opt1", "value")
                .addArgument("potato")
        String[] command = builder.buildCommand()

        then:
        command.length == 7
        command[0] == "gcloud"
        command[1] == "test1"
        command[2] == "test2"
        command[3] == "--bool1"
        command[4] == "--opt1=value"
        command[5] == "tomato"
        command[6] == "potato"
    }

    def "Test auto add gcloud"() {
        given:
        String[] command = []

        when: "Create a command"
        builder.addCommand("test1", "test2")
        command = builder.buildCommand()

        then: "Check it"
        command.length == 3
        command[0] == "gcloud"
        command[1] == "test1"
        command[2] == "test2"
    }

    def "Test bad arguments 1"(String opt, _) {
        when:
        builder.addOption(opt)

        then:
        thrown(IllegalArgumentException)

        where:
        opt   | _
        ""    | _
        "   " | _
        null  | _
    }

    def "Test bad arguments 2"(String opt, String val) {
        when:
        builder.addOption(opt, val)

        then:
        thrown(IllegalArgumentException)

        where:
        opt   | val
        ""    | "bus"
        "   " | "bus"
        null  | "bus"
        ""    | null
        "   " | ""
        null  | null
    }
}
