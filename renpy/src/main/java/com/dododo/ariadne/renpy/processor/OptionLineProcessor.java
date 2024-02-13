package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbState;

import java.util.regex.Matcher;

public final class OptionLineProcessor extends GenericLineProcessor {

    public OptionLineProcessor() {
        super("^'(.+)'\\s*:$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbOption(matcher.group(1), null);
    }
}
