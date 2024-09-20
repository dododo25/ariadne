package com.dododo.ariadne.mxg.geometry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class RelativeNodeGeometry implements AbstractNodeGeometry {

    @XmlAttribute
    private String as;

    @XmlAttribute
    private int relative;

    @XmlAttribute
    private int x;

    @XmlAttribute
    private int y;

    private RelativeNodeGeometry() {}

    @Override
    public int hashCode() {
        return x * 31 + y;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof RelativeNodeGeometry && equals((RelativeNodeGeometry) obj);
    }

    private boolean equals(RelativeNodeGeometry geometry) {
        return geometry.x == this.x
                && geometry.y == this.y;
    }

    public static RelativeNodeGeometry create(int x, int y) {
        RelativeNodeGeometry result = new RelativeNodeGeometry();
        result.as = "geometry";
        result.relative = 1;
        result.x = x;
        result.y = y;
        return result;
    }
}
