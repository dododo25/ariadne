package com.dododo.ariadne.renpy.processor;

import com.dododo.ariadne.renpy.jaxb.model.JaxbState;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class LineProcessor {

    protected final Pattern pattern;

    private LineProcessor next;

    protected LineProcessor(String regex) {
        pattern = Pattern.compile(regex);
    }

    public JaxbState accept(String s) {
        Matcher matcher = pattern.matcher(s);

        if (matcher.find()) {
            return prepareState(matcher);
        }

        return next == null ? null : next.accept(s);
    }

    public abstract JaxbState prepareState(Matcher matcher);

    public void setNext(LineProcessor next) {
        this.next = next;
    }
}
