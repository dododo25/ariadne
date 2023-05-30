package com.dododo.ariadne.renpy.unity.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbJumpToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.processor.GenericLineProcessor;

import java.util.regex.Matcher;

public final class GoToLineProcessor extends GenericLineProcessor {

    public GoToLineProcessor() {
        super("^goto\\s+(\\w+)$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbJumpToState(matcher.group(1));
    }
}
