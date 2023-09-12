package com.dododo.ariadne.mxg.geometry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class EdgeGeometry {

    @XmlAttribute
    private int relative;

    @XmlAttribute
    private String as;

    @XmlElement(name = "mxPoint")
    private final List<GeometryPoint> points;

    private EdgeGeometry() {
        this.points = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return points.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof EdgeGeometry && equals((EdgeGeometry) obj);
    }

    private boolean equals(EdgeGeometry geometry) {
        return Objects.equals(geometry.points, this.points);
    }

    public static EdgeGeometry create(int x0, int y0, int x1, int y1) {
        EdgeGeometry geometry = new EdgeGeometry();
        geometry.relative = 1;
        geometry.as = "geometry";
        geometry.points.add(GeometryPoint.createAsSourcePoint(x0, y0));
        geometry.points.add(GeometryPoint.createAsTargetPoint(x1, y1));
        return geometry;
    }
}
