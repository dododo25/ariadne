package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchBranch;

import java.util.regex.Matcher;

public final class SwitchIfLineProcessor extends GenericLineProcessor {

    public SwitchIfLineProcessor() {
        super("^if\\s+(\\S+(\\s+\\S+)*)\\s*:$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbSwitchBranch(matcher.group(1));
    }
}
