package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.ComplexMenu;

import java.util.regex.Matcher;

public final class MenuLineProcessor extends GenericLineProcessor {

    public MenuLineProcessor() {
        super("^menu(\\s+(.*))?\\s*:$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new ComplexMenu();
    }
}
