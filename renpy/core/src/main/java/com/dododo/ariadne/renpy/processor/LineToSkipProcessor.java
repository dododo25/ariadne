package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.model.SkipComplexState;

import java.util.regex.Matcher;

public final class LineToSkipProcessor extends GenericLineProcessor {

    public LineToSkipProcessor() {
        super("^.*$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new SkipComplexState();
    }
}
