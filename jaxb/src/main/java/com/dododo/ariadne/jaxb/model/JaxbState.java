package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;

public interface JaxbState extends Comparable<JaxbState> {

    void accept(JaxbFlowchartContract contract);

}
