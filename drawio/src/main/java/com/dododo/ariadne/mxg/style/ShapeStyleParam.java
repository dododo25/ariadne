package com.dododo.ariadne.mxg.style;

public final class ShapeStyleParam extends StyleParam {

    public static final StyleParam MXGRAPH_FLOWCHART_OR = new ShapeStyleParam("mxgraph.flowchart.or");
    public static final StyleParam LINK = new ShapeStyleParam("link");

    private ShapeStyleParam(String value) {
        super("shape", value);
    }
}
