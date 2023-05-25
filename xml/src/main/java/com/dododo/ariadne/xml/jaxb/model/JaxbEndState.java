package com.dododo.ariadne.xml.jaxb.model;

import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbEndState implements JaxbState {

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
    }
}
