package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.ComplexOption;

import java.util.regex.Matcher;

public final class OptionLineProcessor extends GenericLineProcessor {

    public OptionLineProcessor() {
        super("^'(.+)'\\s*:$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new ComplexOption(matcher.group(1), null);
    }
}
