package com.dododo.ariadne.mxg.style;

public final class AlignStyleParam extends StyleParam {

    public static final StyleParam LEFT = new AlignStyleParam("left");
    public static final StyleParam RIGHT = new AlignStyleParam("right");

    private AlignStyleParam(String value) {
        super("align", value);
    }
}
