package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.ComplexOption;

import java.util.regex.Matcher;

public final class ConditionalOptionLineProcessor extends GenericLineProcessor {

    public ConditionalOptionLineProcessor() {
        super("^'(.+)'\\s*if\\s+(.+)\\s*:$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        String value = matcher.group(1);
        String condition = matcher.group(2);

        if (condition.equalsIgnoreCase("true")) {
            return new ComplexOption(value, null);
        }

        return new ComplexOption(value, condition.trim());
    }
}
