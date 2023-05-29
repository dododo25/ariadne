package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

import java.util.regex.Matcher;

public final class InvalidLineProcessor extends LineProcessor {

    public static final InvalidLineProcessor INIT_PYTHON_LINE_PROCESSOR
            = new InvalidLineProcessor("^(init\\s+)?(-?[0-9]+\\s)?python\\s*:$");

    public static final InvalidLineProcessor CALL_EXPRESSION_LINE_PROCESSOR
            = new InvalidLineProcessor("^call\\s+expression\\s+\\w+$");

    public static final InvalidLineProcessor RENPY_CALL_LINE_PROCESSOR
            = new InvalidLineProcessor("^\\$\\s*renpy\\s*\\.\\s*call\\s*\\(\\s*'.*'\\s*\\)$");

    InvalidLineProcessor(String regex) {
        super(regex);
    }

    @Override
    public JaxbState prepareState(Matcher matcher) {
        return null;
    }
}
