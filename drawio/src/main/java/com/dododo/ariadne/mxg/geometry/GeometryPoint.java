package com.dododo.ariadne.mxg.geometry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class GeometryPoint {

    @XmlAttribute
    private int x;

    @XmlAttribute
    private int y;

    @XmlAttribute
    private String as;

    private GeometryPoint() {}

    @Override
    public int hashCode() {
        return Objects.hash(x, y, as);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof GeometryPoint && equals((GeometryPoint) obj);
    }

    private boolean equals(GeometryPoint geometry) {
        return Objects.equals(geometry.x, this.x)
                && Objects.equals(geometry.y, this.y)
                && Objects.equals(geometry.as, this.as);
    }

    public static GeometryPoint createAsSourcePoint(int x, int y) {
        GeometryPoint point = new GeometryPoint();
        point.x = x;
        point.y = y;
        point.as = "sourcePoint";
        return point;
    }

    public static GeometryPoint createAsTargetPoint(int x, int y) {
        GeometryPoint point = new GeometryPoint();
        point.x = x;
        point.y = y;
        point.as = "targetPoint";
        return point;
    }
}
