package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.State;

import java.util.regex.Matcher;

public final class ReturnLineProcessor extends GenericLineProcessor {

    public ReturnLineProcessor() {
        super("^return(\\s+.+)?$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new EndPoint();
    }
}
