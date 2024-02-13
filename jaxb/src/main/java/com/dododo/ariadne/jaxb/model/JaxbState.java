package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class JaxbState implements Comparable<JaxbState> {

    @XmlTransient
    private JaxbComplexState root;

    public JaxbComplexState getRoot() {
        return root;
    }

    public void setRoot(JaxbComplexState root) {
        this.root = root;
    }

    public abstract void accept(JaxbFlowchartContract contract);

    public abstract void accept(JaxbFlowchartMouseStrategy strategy, JaxbFlowchartContract callback, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates);

}
