package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbReply;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

import java.util.regex.Matcher;

public final class WithValueCharacterReplyLineProcessor extends TextLineProcessor {

    public WithValueCharacterReplyLineProcessor() {
        super("^([a-zA-Z]\\w*)\\s*'(.+)'$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbReply(matcher.group(1), matcher.group(2));
    }
}
