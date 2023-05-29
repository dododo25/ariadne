package com.dododo.ariadne.renpy.jaxb.model;

import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContract;

public interface JaxbState extends Comparable<JaxbState> {

    void accept(JaxbFlowchartContract contract);

}
