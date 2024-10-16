package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.PassState;

import java.util.regex.Matcher;

public final class SwitchElseLineProcessor extends GenericLineProcessor {

    public SwitchElseLineProcessor() {
        super("^else\\s*:$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        ComplexSwitchBranch result = new ComplexSwitchBranch(null, true);
        result.addChild(new PassState());
        return result;
    }
}
