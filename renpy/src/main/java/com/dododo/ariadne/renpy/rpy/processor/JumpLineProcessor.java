package com.dododo.ariadne.renpy.rpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.processor.GenericLineProcessor;

import java.util.regex.Matcher;

public final class JumpLineProcessor extends GenericLineProcessor {

    public JumpLineProcessor() {
        super("^jump\\s+(\\w+)$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbGoToState(matcher.group(1));
    }
}
