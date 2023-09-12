package com.dododo.ariadne.mxg.style;

public final class WhiteSpaceStyleParam extends StyleParam {

    public static final StyleParam WRAP = new WhiteSpaceStyleParam("wrap");

    private WhiteSpaceStyleParam(String value) {
        super("whiteSpace", value);
    }
}
