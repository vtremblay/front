package org.front;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ConfigurationParser {

    public static final String DEFAULT_BINDING_ADDRESS = "127.0.0.1";
    public static final String DEFAULT_BINDING_PORT = "325224";
    private static ConfigurationParser instance;

    private CommandLineParser commandLineParser;
    private Options options;

    private void ConfigurationParser() {
        commandLineParser = new DefaultParser();
    }

    private void initOptions() {
        options = new Options();
        options.addOption("a", "bind-address", true, "Binding Address");
        options.addOption("p", "bind-port", true, "Binding Port");
    }

    public static ConfigurationParser getInstance() {
        if (instance == null) {
            instance = new ConfigurationParser();
            instance.initOptions();
        }
        return instance;
    }

    public Configuration parse(String[] args) throws ConfigurationException {

        Configuration configuration = new Configuration();

        try {

            CommandLine commandLine = commandLineParser.parse(options, args);

            configuration.setBindingAddress(commandLine.getOptionValue("a", DEFAULT_BINDING_ADDRESS));
            configuration.setBindingPort(Integer.valueOf(commandLine.getOptionValue("p", DEFAULT_BINDING_PORT)));

        } catch (ParseException e) {

            throw new ConfigurationException("Could not load configuration", e);
        }

        return configuration;
    }

    public void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("front", options);
    }

}
