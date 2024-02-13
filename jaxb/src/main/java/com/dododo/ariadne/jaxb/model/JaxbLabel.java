package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbLabel extends JaxbSimpleState {

    @XmlAttribute
    private String value;

    public JaxbLabel() {
        this(null);
    }

    public JaxbLabel(String value) {
        super(value);
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
    }
}
