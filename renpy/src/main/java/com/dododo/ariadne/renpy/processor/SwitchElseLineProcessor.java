package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;

import java.util.regex.Matcher;

public final class SwitchElseLineProcessor extends GenericLineProcessor {

    public SwitchElseLineProcessor() {
        super("^else\\s*:$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbSwitchFalseBranch(null);
    }
}
