package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "flowchart")
@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbRootState extends JaxbComplexState {

    public JaxbRootState() {
        super();
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
    }
}
