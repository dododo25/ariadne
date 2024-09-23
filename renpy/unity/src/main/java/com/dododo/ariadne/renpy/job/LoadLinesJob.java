package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.exception.AriadneException;
import com.dododo.ariadne.core.job.AbstractJob;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public final class LoadLinesJob extends AbstractJob {

    private static final int INDENT = 4;

    private static final String TAB_REPLACEMENT = String.join("", Collections.nCopies(INDENT, " "));

    private final List<String> lines;

    private final int index;

    public LoadLinesJob(int index, List<String> lines) {
        super(String.format("%s_%d", LoadLinesJob.class.getSimpleName(), index));
        this.lines = lines;
        this.index = index;
    }

    @Override
    public void run() {
        Path path = Paths.get(getConfiguration().getInputFiles().get(index));

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line.replaceAll("[\\uFEFF-\\uFFFF]", "").replace("\t", TAB_REPLACEMENT));
            }
        } catch (IOException e) {
            throw new AriadneException(e);
        }
    }
}
