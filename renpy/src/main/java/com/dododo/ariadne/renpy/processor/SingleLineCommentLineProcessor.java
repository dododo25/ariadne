package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbSingleLineComment;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

import java.util.regex.Matcher;

public final class SingleLineCommentLineProcessor extends GenericLineProcessor {

    public SingleLineCommentLineProcessor() {
        super("^\\s*#.*$");
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return new JaxbSingleLineComment();
    }
}
