package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;

import java.util.regex.Matcher;

public final class WithValueCharacterReplyLineProcessor extends GenericLineProcessor {

    public WithValueCharacterReplyLineProcessor() {
        super("^([a-zA-Z]\\w*)\\s*'(.+)'$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new Reply(matcher.group(1), matcher.group(2));
    }
}
