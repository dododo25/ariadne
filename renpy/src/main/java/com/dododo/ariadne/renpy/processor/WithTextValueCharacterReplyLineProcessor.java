package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.jaxb.model.JaxbReply;
import com.dododo.ariadne.jaxb.model.JaxbState;

import java.util.regex.Matcher;

public final class WithTextValueCharacterReplyLineProcessor extends GenericLineProcessor {

    public WithTextValueCharacterReplyLineProcessor() {
        super("^'(.+)'\\s*'(.+)'$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbReply(matcher.group(1).trim(), matcher.group(2).trim());
    }
}
