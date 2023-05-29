package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.common.exception.AriadneException;
import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.renpy.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.processor.LineProcessor;
import com.dododo.ariadne.renpy.processor.LineProcessorFactory;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public final class CollectStatesJob extends AbstractJob {

    private static final int INDENT = 4;

    private static final String TAB_REPLACEMENT = String.join("", Collections.nCopies(INDENT, " "));

    private final int index;

    private final JaxbComplexState rootState;

    private LineProcessor processor;

    public CollectStatesJob(int index, JaxbComplexState rootState) {
        super(String.format("%s_%d", CollectStatesJob.class.getSimpleName(), index));
        this.index = index;
        this.rootState = rootState;
    }

    @Override
    public void run() {
        processor = LineProcessorFactory.create(getConfiguration());
        Path path = Paths.get(getConfiguration().getInputFiles().get(index));

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            for (int i = 0; (line = reader.readLine()) != null; i++) {
                line = line.replaceAll("[\\uFEFF-\\uFFFF]", "").replace("\t", TAB_REPLACEMENT);

                if (!line.matches("^\\s*$")) {
                    prepareRawState(i + 1, line);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void prepareRawState(int index, String line) {
        JaxbState state = processor.accept(line.trim());

        if (state == null) {
            throw new IllegalArgumentException(String.format("Unable to parse '%s' at line %s", line, index));
        }

        int level = getLevel(index, line);
        JaxbComplexState complexState = getLastComplexStateAt(rootState, level, index);

        complexState.addChild(state);
    }

    private static int getLevel(int index, String line) {
        int spacesLength = line.length() - line.replaceFirst("^\\s+", "").length();

        if (spacesLength % INDENT != 0) {
            throw new AriadneException(String.format("Illegal indent at line %d", index));
        }

        return spacesLength / INDENT;
    }

    private static JaxbComplexState getLastComplexStateAt(JaxbComplexState rootState, int level, int index) {
        if (rootState == null) {
            throw new AriadneException(String.format("Illegal level %s at line %d", level, index));
        }

        if (level == 0) {
            return rootState;
        }

        JaxbState state = rootState.childAt(rootState.childrenCount() - 1);

        if (state instanceof JaxbComplexState) {
            return getLastComplexStateAt((JaxbComplexState) state, level - 1, index);
        }

        throw new AriadneException(String.format("Illegal level %s at line %d", level, index));
    }
}
