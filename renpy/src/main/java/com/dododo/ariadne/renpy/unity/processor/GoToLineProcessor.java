package com.dododo.ariadne.renpy.unity.processor;

import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.processor.GenericLineProcessor;

import java.util.regex.Matcher;

public final class GoToLineProcessor extends GenericLineProcessor {

    public GoToLineProcessor() {
        super("^goto\\s+(\\w+)$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbGoToState(matcher.group(1));
    }
}
