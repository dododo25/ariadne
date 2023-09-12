package com.dododo.ariadne.mxg.style;

public final class BooleanStyleParam extends StyleParam {

    private BooleanStyleParam(String key, int value) {
        super(key, value);
    }

    public static StyleParam createAsTrue(Key key) {
        return new BooleanStyleParam(key.text, 1);
    }

    public static StyleParam createAsFalse(Key key) {
        return new BooleanStyleParam(key.text, 0);
    }

    public enum Key {

        CONNECTABLE("connectable"),
        EDITABLE("editable"),
        HTML("html"),
        ORTHOGONAL("orthogonal"),
        ORTHOGONAL_LOOP("orthogonalLoop"),
        RESIZABLE("resizable"),
        RESIZE_WIDTH("resizeWidth"),
        ROUNDED("rounded"),
        TREE_MOVING("treeMoving");

        private final String text;

        Key(String value) {
            this.text = value;
        }
    }
}
