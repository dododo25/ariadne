package com.dododo.ariadne.mxg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class DiagramRoot {

    @XmlElements({
            @XmlElement(name = "mxCell", type = MxNodeCell.class),
            @XmlElement(name = "mxCell", type = MxEdgeCell.class),
            @XmlElement(name = "mxCell", type = RootMxCell.class)
    })
    private final List<MxAbstractCell> cells;

    public DiagramRoot() {
        this.cells = new ArrayList<>();
    }

    public List<MxAbstractCell> getCells() {
        return cells;
    }

    public static DiagramRoot createDefault() {
        DiagramRoot root = new DiagramRoot();

        root.cells.add(new RootMxCell("0", null));
        root.cells.add(new RootMxCell("1", "0"));

        return root;
    }

    @XmlRootElement(name = "mxCell")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class RootMxCell extends MxAbstractCell {

        @XmlAttribute
        private String id;

        @XmlAttribute
        private String parent;

        @SuppressWarnings("unused")
        public RootMxCell() {}

        public RootMxCell(String id, String parent) {
            this.id = id;
            this.parent = parent;
        }

        @Override
        protected String getId() {
            return id;
        }
    }
}
