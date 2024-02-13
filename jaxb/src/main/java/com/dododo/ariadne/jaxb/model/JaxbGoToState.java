package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbGoToState extends JaxbSimpleState {

    public JaxbGoToState() {
        this(null);
    }

    public JaxbGoToState(String value) {
        super(value);
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
    }
}
