package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

import java.util.regex.Matcher;

public final class MenuLineProcessor extends GenericLineProcessor {

    public MenuLineProcessor() {
        super("^menu(\\s+(.*))?\\s*:$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        String value = matcher.group(2);

        if (value == null || value.isEmpty()) {
            return new JaxbMenu();
        }

        return new JaxbMenu(value);
    }
}
