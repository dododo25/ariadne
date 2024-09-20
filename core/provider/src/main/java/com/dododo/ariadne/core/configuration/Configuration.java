package com.dododo.ariadne.core.configuration;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Configuration {

    private final String inputProfile;

    private final String outputProfile;

    private final List<String> inputFiles;

    private final String outputDir;

    private final boolean loadReply;

    private final Set<String> excluded;

    private Configuration(Builder builder) {
        this.inputProfile = builder.inputProfile;
        this.outputProfile = builder.outputProfile;
        this.inputFiles = new ArrayList<>(builder.inputFiles);
        this.outputDir = builder.outputDir;
        this.loadReply = builder.loadReply;
        this.excluded = new HashSet<>(builder.excluded);
    }

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

    public Set<String> getExcluded() {
        return excluded;
    }

    public static class Builder {

        private String inputProfile;

        private String outputProfile;

        private final List<String> inputFiles;

        private String outputDir;

        private boolean loadReply;

        private final Set<String> excluded;

        public Builder() {
            this.outputDir = Paths.get(".").toAbsolutePath().toString();
            this.loadReply = false;

            this.inputFiles = new ArrayList<>();
            this.excluded = new HashSet<>();
        }

        public void setInputProfile(String inputProfile) {
            this.inputProfile = inputProfile;
        }

        public void setOutputProfile(String outputProfile) {
            this.outputProfile = outputProfile;
        }

        public void addInputFile(String inputFile) {
            this.inputFiles.add(inputFile);
        }

        public void setOutputDir(String outputDir) {
            this.outputDir = outputDir;
        }

        public void setLoadReply(boolean loadReply) {
            this.loadReply = loadReply;
        }

        public void addExcluded(String excluded) {
            this.excluded.add(excluded);
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }
}
