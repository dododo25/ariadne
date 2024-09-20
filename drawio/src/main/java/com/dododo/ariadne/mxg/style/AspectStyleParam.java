package com.dododo.ariadne.mxg.style;

public final class AspectStyleParam extends StyleParam {

    public static final StyleParam FIXED = new AspectStyleParam("fixed");

    private AspectStyleParam(String value) {
        super("aspect", value);
    }
}
