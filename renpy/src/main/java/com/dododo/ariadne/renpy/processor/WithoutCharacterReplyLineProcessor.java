package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;

import java.util.regex.Matcher;

public final class WithoutCharacterReplyLineProcessor extends GenericLineProcessor {

    public WithoutCharacterReplyLineProcessor() {
        super("^'([^']+)'$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new Reply(null, matcher.group(1).trim());
    }
}
