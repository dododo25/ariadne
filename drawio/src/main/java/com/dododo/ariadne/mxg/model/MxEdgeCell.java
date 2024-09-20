package com.dododo.ariadne.mxg.model;

import com.dododo.ariadne.mxg.geometry.EdgeGeometry;
import com.dododo.ariadne.mxg.style.StyleParam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@XmlAccessorType(XmlAccessType.FIELD)
public class MxEdgeCell extends MxAbstractCell {

    @XmlAttribute
    private static final int EDGE = 1;

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String parent;

    @XmlAttribute
    private String source;

    @XmlAttribute
    private String target;

    @XmlAttribute
    private String style;

    @XmlElement(name = "mxGeometry")
    private EdgeGeometry geometry;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parent, source, target, style, geometry);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof MxEdgeCell && equals((MxEdgeCell) obj);
    }

    private boolean equals(MxEdgeCell cell) {
        return Objects.equals(cell.id, this.id)
                && Objects.equals(cell.parent, this.parent)
                && Objects.equals(cell.source, this.source)
                && Objects.equals(cell.target, this.target)
                && Objects.equals(cell.geometry, this.geometry);
    }

    public static class Builder {

        private int source;

        private int target;

        private String idSuffix;

        private final Map<String, Object> styleParams;

        private EdgeGeometry geometry;

        public Builder() {
            this.idSuffix = "";
            this.styleParams = new HashMap<>();
            this.geometry = EdgeGeometry.create(0, 0, 0, 0);
        }
        public Builder setSource(int source) {
            this.source = source;
            return this;
        }

        public Builder setTarget(int target) {
            this.target = target;
            return this;
        }

        public Builder setIdSuffix(String suffix) {
            this.idSuffix = suffix;
            return this;
        }

        public Builder setEntryPoint(double x, double y) {
            this.styleParams.put("entryX", x);
            this.styleParams.put("entryY", y);
            return this;
        }

        public Builder setExitPoint(double x, double y) {
            this.styleParams.put("exitX", x);
            this.styleParams.put("exitY", y);
            return this;
        }

        public Builder addStyleParam(StyleParam param) {
            this.styleParams.put(param.getKey(), param.getValue());
            return this;
        }

        public Builder setGeometry(EdgeGeometry geometry) {
            this.geometry = geometry;
            return this;
        }

        public MxEdgeCell build() {
            MxEdgeCell cell = new MxEdgeCell();

            cell.id = String.format("%d-%d%s", source + 2, target + 2, idSuffix);
            cell.parent = "1";
            cell.source = String.valueOf(source + 2);
            cell.target = String.valueOf(target + 2);
            cell.geometry = geometry;
            cell.style = styleParams.entrySet()
                    .stream()
                    .map(entry -> prepareStyleString(entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining(";"));

            return cell;
        }
    }

    public static class NoVerticesBuilder {

        private long id;

        private String idSuffix;

        private int parent;

        private final Map<String, Object> styleParams;

        private EdgeGeometry geometry;

        public NoVerticesBuilder() {
            this.idSuffix = "";
            this.parent = -1;
            this.styleParams = new HashMap<>();
            this.geometry = EdgeGeometry.create(0, 0, 0, 0);
        }

        public NoVerticesBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public NoVerticesBuilder setIdSuffix(String suffix) {
            this.idSuffix = suffix;
            return this;
        }

        public NoVerticesBuilder setParent(int parent) {
            this.parent = parent;
            return this;
        }

        public NoVerticesBuilder addStyleParam(StyleParam param) {
            this.styleParams.put(param.getKey(), param.getValue());
            return this;
        }

        public NoVerticesBuilder setGeometry(EdgeGeometry geometry) {
            this.geometry = geometry;
            return this;
        }

        public MxEdgeCell build() {
            MxEdgeCell cell = new MxEdgeCell();

            cell.id = String.format("%d%s", id + 2, idSuffix);
            cell.parent = String.valueOf(parent + 2);
            cell.geometry = geometry;
            cell.style = styleParams.entrySet()
                    .stream()
                    .map(entry -> prepareStyleString(entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining(";"));

            return cell;
        }
    }
}
