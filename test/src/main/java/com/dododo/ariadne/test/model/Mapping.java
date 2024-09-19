package com.dododo.ariadne.test.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public final class Mapping {

    @XmlElement(name = "root-node", type = Node.class)
    private Node rootNode;

    @XmlElement(name = "node", type = Node.class)
    private List<Node> nodes;

    @XmlElement(name = "edge", type = Edge.class)
    private List<Edge> edges;

    public Mapping() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public Node getRootNode() {
        return rootNode;
    }

    public Collection<Node> getNodes() {
        return nodes;
    }

    public Collection<Edge> getEdges() {
        return edges;
    }
}
