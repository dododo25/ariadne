package com.dododo.ariadne.drawio.mxg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Diagram {

    @XmlAttribute
    private String name;

    @XmlElement(name = "mxGraphModel")
    private MxGraphModel model;

    public MxGraphModel getModel() {
        return model;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModel(MxGraphModel model) {
        this.model = model;
    }
}
