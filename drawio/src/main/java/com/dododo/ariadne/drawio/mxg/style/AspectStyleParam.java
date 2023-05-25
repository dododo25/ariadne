package com.dododo.ariadne.drawio.mxg.style;

public final class AspectStyleParam extends StyleParam {

    public static final StyleParam FIXED = new AspectStyleParam("fixed");

    private AspectStyleParam(String value) {
        super("aspect", value);
    }
}
