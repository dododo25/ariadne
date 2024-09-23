package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.core.model.State;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class GenericLineProcessor extends LineProcessor {

    protected final Pattern pattern;

    protected GenericLineProcessor(String regex) {
        pattern = Pattern.compile(regex);
    }

    @Override
    protected State prepareState(String s) {
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            return prepareState(matcher);
        }

        return null;
    }

    protected abstract State prepareState(Matcher matcher);
}
