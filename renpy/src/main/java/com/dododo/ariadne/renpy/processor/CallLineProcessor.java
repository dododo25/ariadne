package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbCallToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

import java.util.regex.Matcher;

public final class CallLineProcessor extends LineProcessor {

    public CallLineProcessor() {
        super("^call\\s+(\\w+)(\\(.*\\))?$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbCallToState(matcher.group(1));
    }
}
