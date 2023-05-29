package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbOption;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

import java.util.regex.Matcher;

public final class ConditionalOptionLineProcessor extends TextLineProcessor {

    public ConditionalOptionLineProcessor() {
        super("^'(.+)'\\s*if\\s+(.+)\\s*:$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        String value = matcher.group(1);
        String condition = matcher.group(2);

        if (condition.equalsIgnoreCase("true")) {
            return new JaxbOption(value, null);
        }

        return new JaxbOption(value, condition.trim());
    }
}
