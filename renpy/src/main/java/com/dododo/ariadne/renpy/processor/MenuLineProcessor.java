package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

import java.util.regex.Matcher;

public final class MenuLineProcessor extends LineProcessor {

    public MenuLineProcessor() {
        super("^menu(\\(.*\\))? *:$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbMenu();
    }
}
