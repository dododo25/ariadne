package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.PassState;

import java.util.regex.Matcher;

public final class SingleLineCommentLineProcessor extends GenericLineProcessor {

    public SingleLineCommentLineProcessor() {
        super("^\\s*#.*$");
    }

    @Override
    public State prepareState(Matcher matcher) {
        return new PassState();
    }
}
