package com.dododo.ariadne.xml.jaxb.model;

import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbGoToState implements JaxbSimpleState {

    @XmlAttribute
    private String value;

    public JaxbGoToState() {}

    public JaxbGoToState(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
    }
}
