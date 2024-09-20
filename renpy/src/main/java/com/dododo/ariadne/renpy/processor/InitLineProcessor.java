package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.model.VariableGroupComplexState;

import java.util.regex.Matcher;

public final class InitLineProcessor extends GenericLineProcessor {

    public InitLineProcessor() {
        super("^init\\s*:$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new VariableGroupComplexState();
    }
}
