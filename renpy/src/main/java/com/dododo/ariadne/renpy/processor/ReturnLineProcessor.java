package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbEndState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

import java.util.regex.Matcher;

public final class ReturnLineProcessor extends LineProcessor {

    public ReturnLineProcessor() {
        super("^return(\\s+.+)?$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbEndState();
    }
}
