package com.dododo.ariadne.mxg.model;

import com.dododo.ariadne.mxg.geometry.AbstractNodeGeometry;
import com.dododo.ariadne.mxg.geometry.ComplexNodeGeometry;
import com.dododo.ariadne.mxg.geometry.RelativeNodeGeometry;
import com.dododo.ariadne.mxg.style.StyleParam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@XmlAccessorType(XmlAccessType.FIELD)
public class MxNodeCell extends MxAbstractCell {

    @XmlAttribute
    private static final int VERTEX = 1;

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String value;

    @XmlAttribute
    private String parent;

    @XmlAttribute
    private String style;

    @XmlElements({
            @XmlElement(name = "mxGeometry", type = RelativeNodeGeometry.class),
            @XmlElement(name = "mxGeometry", type = ComplexNodeGeometry.class),
    })
    private AbstractNodeGeometry geometry;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, parent, style, geometry);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof MxNodeCell && equals((MxNodeCell) obj);
    }

    private boolean equals(MxNodeCell cell) {
        return Objects.equals(cell.id, this.id)
                && Objects.equals(cell.value, this.value)
                && Objects.equals(cell.parent, this.parent)
                && Objects.equals(cell.geometry, this.geometry);
    }

    public static class Builder {

        private long id;

        private String idSuffix;

        private String value;

        private long parent;

        private final Map<String, Object> styleParams;

        private AbstractNodeGeometry geometry;

        public Builder() {
            this.idSuffix = "";
            this.parent = -1;
            this.value = "";
            this.styleParams = new HashMap<>();
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setIdSuffix(String suffix) {
            this.idSuffix = suffix;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setParent(int id) {
            this.parent = id;
            return this;
        }

        public Builder addStyleParam(StyleParam param) {
            this.styleParams.put(param.getKey(), param.getValue());
            return this;
        }

        public Builder setGeometry(AbstractNodeGeometry geometry) {
            this.geometry = geometry;
            return this;
        }

        public MxNodeCell build() {
            MxNodeCell cell = new MxNodeCell();

            cell.id = String.format("%d%s", id + 2, idSuffix);
            cell.value = value;
            cell.parent = String.valueOf(parent + 2);
            cell.geometry = geometry;
            cell.style = styleParams.entrySet()
                    .stream()
                    .map(entry -> prepareStyleString(entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining(";"));

            return cell;
        }
    }
    public static class ParentCellBuilder {

        private MxAbstractCell parent;

        private String idSuffix;

        private String value;

        private final Map<String, Object> styleParams;

        private AbstractNodeGeometry geometry;

        public ParentCellBuilder() {
            this.idSuffix = "";
            this.value = "";
            this.styleParams = new HashMap<>();
        }

        public ParentCellBuilder setId(MxAbstractCell parent, String suffix) {
            this.parent = parent;
            this.idSuffix = suffix;
            return this;
        }

        public ParentCellBuilder setValue(String value) {
            this.value = value;
            return this;
        }

        public ParentCellBuilder addStyleParam(StyleParam param) {
            this.styleParams.put(param.getKey(), param.getValue());
            return this;
        }

        public ParentCellBuilder setGeometry(AbstractNodeGeometry geometry) {
            this.geometry = geometry;
            return this;
        }

        public MxNodeCell build() {
            MxNodeCell cell = new MxNodeCell();

            cell.id = parent.getId() + idSuffix;
            cell.value = value;
            cell.parent = parent.getId();
            cell.geometry = geometry;
            cell.style = styleParams.entrySet()
                    .stream()
                    .map(entry -> prepareStyleString(entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining(";"));

            return cell;
        }
    }
}
