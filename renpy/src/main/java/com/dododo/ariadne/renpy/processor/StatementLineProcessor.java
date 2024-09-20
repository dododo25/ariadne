package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;

import java.util.regex.Matcher;

public final class StatementLineProcessor extends GenericLineProcessor {

    public StatementLineProcessor() {
        super("^\\$\\s*(\\S+(\\s+\\S+)*)$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new Text(matcher.group(1));
    }
}
