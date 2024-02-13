package com.dododo.ariadne.renpy.rpy.job;

import com.dododo.ariadne.common.exception.AriadneException;
import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSingleLineComment;
import com.dododo.ariadne.renpy.processor.LineProcessor;
import com.dododo.ariadne.renpy.rpy.processor.LineProcessorFactory;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CollectStatesJob extends AbstractJob {

    private static final int INDENT = 4;

    private static final String TAB_REPLACEMENT = String.join("", Collections.nCopies(INDENT, " "));

    private final int index;

    private final JaxbComplexState rootState;

    private LineProcessor processor;

    private LinkedList<Integer> levels;

    public CollectStatesJob(int index, JaxbComplexState rootState) {
        super(String.format("%s_%d", CollectStatesJob.class.getSimpleName(), index));
        this.index = index;
        this.rootState = rootState;
    }

    @Override
    public void run() {
        processor = LineProcessorFactory.create(getConfiguration());
        levels = new LinkedList<>();

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
            throw new AriadneException(e);
        }
    }

    private void prepareRawState(int index, String line) {
        JaxbState state = processor.accept(line.trim());

        if (state == null) {
            throw new IllegalArgumentException(String.format("Unable to parse '%s' at line %s", line, index));
        } else if (state instanceof JaxbSingleLineComment) {
            return;
        }

        processLineLevel(line);
        addState(rootState, state, index);
    }

    private void processLineLevel(String line) {
        Matcher matcher = Pattern.compile("^(\\s+).*$").matcher(line);

        int spacesCount = matcher.find() ? matcher.group(1).length() : 0;

        while (!levels.isEmpty() && levels.getLast() >= spacesCount) {
            levels.removeLast();
        }

        levels.add(spacesCount);
    }

    private void addState(JaxbComplexState rootState, JaxbState state, int index) {
        JaxbComplexState currentComplexState = rootState;

        for (int i = 0; i < levels.size() - 1; i++) {
            if (currentComplexState.childrenCount() == 0) {
                throw new AriadneException(String.format("Illegal indent at line %d", index));
            }

            JaxbState nextState = currentComplexState.childAt(currentComplexState.childrenCount() - 1);

            if (!(nextState instanceof JaxbComplexState)) {
                throw new AriadneException(String.format("Illegal indent at line %d", index));
            }

            currentComplexState = (JaxbComplexState) nextState;
        }

        currentComplexState.addChild(state);
    }
}
