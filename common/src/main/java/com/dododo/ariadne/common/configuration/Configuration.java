package com.dododo.ariadne.common.configuration;

import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Configuration {

    private static final Comparator<String> KEYS_COMPARATOR = Comparator
            .comparingInt(o -> Integer.parseInt(o.substring(o.lastIndexOf('.') + 1)));

    private String inputProfile;

    private String outputProfile;

    private List<String> inputFiles;

    private String outputDir;

    private boolean loadReply;

    private Configuration() {}

    public String getInputProfile() {
        return inputProfile;
    }

    public String getOutputProfile() {
        return outputProfile;
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public boolean isLoadReply() {
        return loadReply;
    }

    public static Configuration create(Properties properties) {
        Configuration configuration = new Configuration();

        configuration.inputProfile  = properties.getProperty("flowchart.input.profile");
        configuration.outputProfile = properties.getProperty("flowchart.output.profile");
        configuration.outputDir     = properties.getProperty("flowchart.output.directory");
        configuration.inputFiles    = prepareSortedInputFilesList(properties);
        configuration.loadReply     = Boolean.parseBoolean(properties.getProperty("flowchart.loadReply", "true"));

        return configuration;
    }

    private static List<String> prepareSortedInputFilesList(Properties properties) {
        return properties.stringPropertyNames()
                .stream()
                .filter(key -> key.startsWith("flowchart.input.file"))
                .sorted(KEYS_COMPARATOR)
                .map(properties::getProperty)
                .collect(Collectors.toList());
    }
}
