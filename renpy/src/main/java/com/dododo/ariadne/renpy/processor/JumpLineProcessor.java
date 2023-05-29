package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbJumpToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

import java.util.regex.Matcher;

public final class JumpLineProcessor extends LineProcessor {

    public JumpLineProcessor() {
        super("^jump\\s+(\\w+)$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbJumpToState(matcher.group(1));
    }
}
