package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.exception.AriadneException;
import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.renpy.model.RootComplexState;
import com.dododo.ariadne.renpy.processor.LineProcessor;
import com.dododo.ariadne.renpy.processor.UnityLineProcessorFactory;

import java.util.List;

public final class UnityCollectStatesJob extends AbstractJob {

    private static final int INDENT = 4;

    private LineProcessor processor;

    private final List<String> lines;

    public UnityCollectStatesJob(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public void run() {
        processor = UnityLineProcessorFactory.create(getConfiguration());

        lines.stream()
                .filter(line -> !line.matches("^\\s*$"))
                .forEach(this::prepareRawState);
    }

    private void prepareRawState(String line) {
        State state = processor.accept(line.trim());

        if (state == null) {
            throw new IllegalArgumentException(String.format("Unable to parse '%s'", line));
        }

        int level = getLevel(line);
        ComplexState complexState = getLastComplexStateAt((RootComplexState) getFlowchart(), level);

        complexState.addChild(state);
    }

    private static int getLevel(String line) {
        int spacesLength = line.length() - line.replaceFirst("^\\s+", "").length();

        if (spacesLength % INDENT != 0) {
            throw new AriadneException("Illegal indent");
        }

        return spacesLength / INDENT;
    }

    private static ComplexState getLastComplexStateAt(ComplexState rootState, int level) {
        if (rootState == null) {
            throw new AriadneException(String.format("Illegal level %s", level));
        }

        ComplexState current = rootState;

        for (int i = 0; i < level; i++) {
            State child = current.childAt(current.childrenCount() - 1);

            if (child instanceof ComplexState) {
                current = (ComplexState) child;
            } else {
                throw new AriadneException(String.format("Illegal level %s", level));
            }
        }

        return current;
    }
}
