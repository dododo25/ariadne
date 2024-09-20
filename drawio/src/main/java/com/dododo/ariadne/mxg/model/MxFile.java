package com.dododo.ariadne.mxg.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mxfile")
@XmlAccessorType(XmlAccessType.FIELD)
public class MxFile {

    @XmlAttribute
    private String host;

    @XmlAttribute
    private String version;

    @XmlAttribute
    private String type;

    @XmlElement(name = "diagram")
    private Diagram diagram;

    public Diagram getDiagram() {
        return diagram;
    }

    public void setDiagram(Diagram diagram) {
        this.diagram = diagram;
    }

    public static MxFile createDefault() {
        MxFile mxFile = new MxFile();

        mxFile.host = "Electron";
        mxFile.version = "16.1.2";
        mxFile.type = "device";

        return mxFile;
    }
}
