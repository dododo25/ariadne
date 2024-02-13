package com.dododo.ariadne.renpy.rpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.processor.GenericLineProcessor;

import java.util.regex.Matcher;

public final class InitLineProcessor extends GenericLineProcessor {

    public InitLineProcessor() {
        super("^init\\s*:$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbInitGroupState();
    }
}
