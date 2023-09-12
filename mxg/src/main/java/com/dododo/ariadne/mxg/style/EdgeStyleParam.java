package com.dododo.ariadne.mxg.style;

public final class EdgeStyleParam extends StyleParam {

    public static final StyleParam ORTHOGONAL_EDGE_STYLE = new EdgeStyleParam("orthogonalEdgeStyle");

    private EdgeStyleParam(String value) {
        super("edgeStyle", value);
    }
}
