package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.model.CallToState;

import java.util.regex.Matcher;

public final class CallLineProcessor extends GenericLineProcessor {

    public CallLineProcessor() {
        super("^call\\s+(\\w+)(\\(.*\\))?$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new CallToState(matcher.group(1));
    }
}
