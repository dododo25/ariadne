package com.dododo.ariadne.renpy.rpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbCallToState;
import com.dododo.ariadne.renpy.processor.GenericLineProcessor;

import java.util.regex.Matcher;

public final class CallLineProcessor extends GenericLineProcessor {

    public CallLineProcessor() {
        super("^call\\s+(\\w+)(\\(.*\\))?$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbCallToState(matcher.group(1));
    }
}
