package com.dododo.ariadne.drawio.mxg.style;

public final class StrokeColorStyleParam extends StyleParam {

    public static final StyleParam NONE = new StrokeColorStyleParam("none");

    private StrokeColorStyleParam(String value) {
        super("strokeColor", value);
    }
}
