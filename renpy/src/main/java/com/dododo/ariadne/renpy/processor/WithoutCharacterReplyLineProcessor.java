package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbReply;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

import java.util.regex.Matcher;

public final class WithoutCharacterReplyLineProcessor extends TextLineProcessor {

    public WithoutCharacterReplyLineProcessor() {
        super("^'([^']+)'$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbReply(null, matcher.group(1).trim());
    }
}
