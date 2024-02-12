package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;

import java.util.Collection;

public interface JaxbState extends Comparable<JaxbState> {

    void accept(JaxbFlowchartContract contract);

    void accept(JaxbFlowchartMouseStrategy strategy, JaxbFlowchartContract callback, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates);

}
