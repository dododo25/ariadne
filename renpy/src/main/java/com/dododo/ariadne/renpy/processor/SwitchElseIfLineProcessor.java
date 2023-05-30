package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;

import java.util.regex.Matcher;

public final class SwitchElseIfLineProcessor extends GenericLineProcessor {

    public SwitchElseIfLineProcessor() {
        super("^elif\\s+(\\S+(\\s+\\S+)*)\\s*:$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbSwitchFalseBranch(matcher.group(1));
    }
}
