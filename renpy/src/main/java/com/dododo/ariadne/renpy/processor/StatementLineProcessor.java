package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbStatement;

import java.util.regex.Matcher;

public final class StatementLineProcessor extends GenericLineProcessor {

    public StatementLineProcessor() {
        super("^\\$\\s*(\\S+(\\s+\\S+)*)$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbStatement(matcher.group(1));
    }
}
