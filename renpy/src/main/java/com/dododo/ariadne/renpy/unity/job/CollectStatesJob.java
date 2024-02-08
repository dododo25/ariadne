package com.dododo.ariadne.renpy.unity.job;

import com.dododo.ariadne.common.exception.AriadneException;
import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.processor.LineProcessor;
import com.dododo.ariadne.renpy.unity.processor.LineProcessorFactory;

import java.util.List;

public final class CollectStatesJob extends AbstractJob {

    private static final int INDENT = 4;

    private LineProcessor processor;

    private final JaxbComplexState rootState;

    private final List<String> lines;

    public CollectStatesJob(JaxbComplexState rootState, List<String> lines) {
        this.rootState = rootState;
        this.lines = lines;
    }

    @Override
    public void run() {
        processor = LineProcessorFactory.create(getConfiguration());

        lines.stream()
                .filter(line -> !line.matches("^\\s*$"))
                .forEach(this::prepareRawState);
    }

    private void prepareRawState(String line) {
        JaxbState state = processor.accept(line.trim());

        if (state == null) {
            throw new IllegalArgumentException(String.format("Unable to parse '%s'", line));
        }

        int level = getLevel(line);
        JaxbComplexState complexState = getLastComplexStateAt(rootState, level);

        complexState.addChild(state);
    }

    private static int getLevel(String line) {
        int spacesLength = line.length() - line.replaceFirst("^\\s+", "").length();

        if (spacesLength % INDENT != 0) {
            throw new AriadneException("Illegal indent");
        }

        return spacesLength / INDENT;
    }

    private static JaxbComplexState getLastComplexStateAt(JaxbComplexState rootState, int level) {
        if (rootState == null) {
            throw new AriadneException(String.format("Illegal level %s", level));
        }

        if (level == 0) {
            return rootState;
        }

        JaxbState state = rootState.childAt(rootState.childrenCount() - 1);

        if (state instanceof JaxbComplexState) {
            return getLastComplexStateAt((JaxbComplexState) state, level - 1);
        }

        throw new AriadneException(String.format("Illegal level %s", level));
    }
}
