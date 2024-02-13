package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSkipComplexState;

import java.util.regex.Matcher;

public final class LineToSkipProcessor extends GenericLineProcessor {

    public LineToSkipProcessor() {
        super("^.*$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbSkipComplexState();
    }
}
