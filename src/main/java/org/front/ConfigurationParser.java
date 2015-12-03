package org.front;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

@SuppressWarnings("squid:S1313")
public class ConfigurationParser {

    private static final String DEFAULT_BINDING_ADDRESS = "127.0.0.1";
    private static final String DEFAULT_BINDING_PORT = "5544";

    private static ConfigurationParser instance;

    private CommandLineParser commandLineParser;
    private Options options;

    private ConfigurationParser() {
    }

    private void initialize() {

        commandLineParser = new DefaultParser();

        options = new Options();
        options.addOption("a", "address", true, "Binding Address");
        options.addOption("p", "port", true, "Binding Port");
    }

    public static ConfigurationParser getInstance() {

        if (instance == null) {
            instance = new ConfigurationParser();
            instance.initialize();
        }

        return instance;
    }

    public Configuration parse(String[] args) {

        Configuration configuration = new Configuration();

        try {

            CommandLine commandLine = commandLineParser.parse(options, args);

            configuration.setBindingAddress(commandLine.getOptionValue("a", DEFAULT_BINDING_ADDRESS));
            configuration.setBindingPort(Integer.valueOf(commandLine.getOptionValue("p", DEFAULT_BINDING_PORT)));

        } catch (ParseException | NumberFormatException e) {

            throw new IllegalArgumentException("Could not load configuration", e);
        }

        return configuration;
    }

    public void printUsage() {

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("front", options);
    }

}
