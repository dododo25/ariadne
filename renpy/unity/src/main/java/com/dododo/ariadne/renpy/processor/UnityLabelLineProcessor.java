package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;

import java.util.regex.Matcher;

public final class UnityLabelLineProcessor extends GenericLineProcessor {

    public UnityLabelLineProcessor() {
        super("^label\\s+([a-zA-Z]\\w*)$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        ComplexState complexState = new LabelledGroupComplexState(matcher.group(1));
        complexState.addChild(new PassState());
        return complexState;
    }
}
