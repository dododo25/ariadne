package com.dododo.ariadne.xml.jaxb.model;

import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContract;

public interface JaxbState extends Comparable<JaxbState> {

    void accept(JaxbFlowchartContract contract);

}
