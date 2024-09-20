package com.dododo.ariadne.mxg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MxGraphModel {

    @XmlAttribute
    private int grid;

    @XmlAttribute
    private int gridSize;

    @XmlAttribute
    private int guides;

    @XmlAttribute
    private int tooltips;

    @XmlAttribute
    private int connect;

    @XmlAttribute
    private int arrows;

    @XmlAttribute
    private int fold;

    @XmlAttribute
    private int page;

    @XmlAttribute
    private int pageScale;

    @XmlAttribute
    private int pageWidth;

    @XmlAttribute
    private int pageHeight;

    @XmlAttribute
    private int math;

    @XmlAttribute
    private int shadow;

    @XmlElement
    private DiagramRoot root;

    public DiagramRoot getRoot() {
        return root;
    }

    public void setRoot(DiagramRoot root) {
        this.root = root;
    }

    public static MxGraphModel createDefault() {
        MxGraphModel model = new MxGraphModel();

        model.grid = 1;
        model.gridSize = 10;
        model.guides = 1;
        model.tooltips = 1;
        model.connect = 1;
        model.arrows = 1;
        model.fold = 1;
        model.page = 1;
        model.pageScale = 1;
        model.pageWidth = 720;
        model.pageHeight = 1280;
        model.math = 0;
        model.shadow = 0;

        return model;
    }
}
