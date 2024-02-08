package com.dododo.ariadne.renpy.rpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.processor.GenericLineProcessor;

import java.util.regex.Matcher;

public final class LabelLineProcessor extends GenericLineProcessor {

    public LabelLineProcessor() {
        super("^label\\s+([a-zA-Z]\\w*)(\\(.*\\))?\\s*:$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbLabelledGroup(matcher.group(1));
    }
}
