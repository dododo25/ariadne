package com.dododo.ariadne.mxg.style;

public final class SingleKeyStyleParam extends StyleParam {

    public static final StyleParam EDGE_LABEL = new SingleKeyStyleParam("edgeLabel");
    public static final StyleParam ELLIPSE = new SingleKeyStyleParam("ellipse");
    public static final StyleParam GROUP = new SingleKeyStyleParam("group");
    public static final StyleParam RHOMBUS = new SingleKeyStyleParam("rhombus");

    private SingleKeyStyleParam(String key) {
        super(key, null);
    }
}
