package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;

import java.util.regex.Matcher;

public final class RenPyLabelLineProcessor extends GenericLineProcessor {

    public RenPyLabelLineProcessor() {
        super("^label\\s+([a-zA-Z]\\w*)(\\(.*\\))?\\s*:$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        LabelledGroupComplexState result = new LabelledGroupComplexState(matcher.group(1));
        result.addChild(new PassState());
        return result;
    }
}
