package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbText;

import java.util.regex.Matcher;

public final class StatementLineProcessor extends GenericLineProcessor {

    public StatementLineProcessor() {
        super("^\\$\\s*(\\S+(\\s+\\S+)*)$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbText(matcher.group(1));
    }
}
