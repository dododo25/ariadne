package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;

import java.util.regex.Matcher;

public final class SwitchElseLineProcessor extends GenericLineProcessor {

    public SwitchElseLineProcessor() {
        super("^else\\s*:$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new ComplexSwitchBranch(null, true);
    }
}
