package com.dododo.ariadne.mxg.geometry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class ComplexNodeGeometry implements AbstractNodeGeometry {

    @XmlAttribute
    private String as;

    @XmlAttribute
    private int x;

    @XmlAttribute
    private int y;

    @XmlAttribute
    private int width;

    @XmlAttribute
    private int height;

    private ComplexNodeGeometry() {}

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof ComplexNodeGeometry && equals((ComplexNodeGeometry) obj);
    }

    private boolean equals(ComplexNodeGeometry geometry) {
        return geometry.x == this.x
                && geometry.y == this.y
                && geometry.width == this.width
                && geometry.height == this.height;
    }

    public static ComplexNodeGeometry create(int x, int y, int width, int height) {
        ComplexNodeGeometry geometry = new ComplexNodeGeometry();
        geometry.as = "geometry";
        geometry.x = x;
        geometry.y = y;
        geometry.width = width;
        geometry.height = height;
        return geometry;
    }
}
