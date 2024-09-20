package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;

import java.util.regex.Matcher;

public final class SwitchElseIfLineProcessor extends GenericLineProcessor {

    public SwitchElseIfLineProcessor() {
        super("^elif\\s+(\\S+(\\s+\\S+)*)\\s*:$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new ComplexSwitchBranch(matcher.group(1), true);
    }
}
