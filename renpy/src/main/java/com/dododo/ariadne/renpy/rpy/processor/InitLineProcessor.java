package com.dododo.ariadne.renpy.rpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.processor.GenericLineProcessor;

import java.util.regex.Matcher;

public final class InitLineProcessor extends GenericLineProcessor {

    public InitLineProcessor() {
        super("^init\\s*:$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbGroupState();
    }
}
