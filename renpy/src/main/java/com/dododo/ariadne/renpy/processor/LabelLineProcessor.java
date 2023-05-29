package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

import java.util.regex.Matcher;

public final class LabelLineProcessor extends LineProcessor {

    public LabelLineProcessor() {
        super("^label\\s+([a-zA-Z]\\w*)(\\(.*\\))?\\s*:$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbLabelledGroup(matcher.group(1));
    }
}
