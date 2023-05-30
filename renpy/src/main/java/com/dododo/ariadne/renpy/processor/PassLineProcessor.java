package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbPassState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

import java.util.regex.Matcher;

public final class PassLineProcessor extends GenericLineProcessor {

    public PassLineProcessor() {
        super("^pass$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbPassState();
    }
}
