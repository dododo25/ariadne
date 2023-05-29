package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbPassState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

import java.util.regex.Matcher;

final class PassLineProcessor extends LineProcessor {

    public PassLineProcessor() {
        super("^pass$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbPassState();
    }
}
