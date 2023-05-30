package com.dododo.ariadne.renpy.rpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbJumpToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.processor.GenericLineProcessor;

import java.util.regex.Matcher;

public final class JumpLineProcessor extends GenericLineProcessor {

    public JumpLineProcessor() {
        super("^jump\\s+(\\w+)$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbJumpToState(matcher.group(1));
    }
}
