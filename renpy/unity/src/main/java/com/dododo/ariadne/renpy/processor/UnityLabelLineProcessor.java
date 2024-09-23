package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;

import java.util.regex.Matcher;

public final class UnityLabelLineProcessor extends GenericLineProcessor {

    public UnityLabelLineProcessor() {
        super("^label\\s+([a-zA-Z]\\w*)$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new LabelledGroupComplexState(matcher.group(1));
    }
}
