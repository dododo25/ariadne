package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.PassState;

import java.util.regex.Matcher;

public final class SwitchElseIfLineProcessor extends GenericLineProcessor {

    public SwitchElseIfLineProcessor() {
        super("^elif\\s+(\\S+(\\s+\\S+)*)\\s*:$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        ComplexSwitchBranch result = new ComplexSwitchBranch(matcher.group(1), true);
        result.addChild(new PassState());
        return result;
    }
}
