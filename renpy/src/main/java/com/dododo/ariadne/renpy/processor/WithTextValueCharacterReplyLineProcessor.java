package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;

import java.util.regex.Matcher;

public final class WithTextValueCharacterReplyLineProcessor extends GenericLineProcessor {

    public WithTextValueCharacterReplyLineProcessor() {
        super("^'(.+)'\\s*'(.+)'$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new Reply(matcher.group(1).trim(), matcher.group(2).trim());
    }
}
