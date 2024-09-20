package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;

import java.util.regex.Matcher;

public final class SwitchIfLineProcessor extends GenericLineProcessor {

    public SwitchIfLineProcessor() {
        super("^if\\s+(\\S+(\\s+\\S+)*)\\s*:$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new ComplexSwitchBranch(matcher.group(1), false);
    }
}
