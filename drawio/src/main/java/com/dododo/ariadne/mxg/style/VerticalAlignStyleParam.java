package com.dododo.ariadne.mxg.style;

public final class VerticalAlignStyleParam extends StyleParam {

    public static final StyleParam TOP = new VerticalAlignStyleParam("top");

    private VerticalAlignStyleParam(String value) {
        super("verticalAlign", value);
    }
}
