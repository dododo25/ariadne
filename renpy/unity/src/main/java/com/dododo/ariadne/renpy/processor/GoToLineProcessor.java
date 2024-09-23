package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.GoToPoint;

import java.util.regex.Matcher;

public final class GoToLineProcessor extends GenericLineProcessor {

    public GoToLineProcessor() {
        super("^goto\\s+(\\w+)$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new GoToPoint(matcher.group(1));
    }
}
