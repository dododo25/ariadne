package com.dododo.ariadne.renpy.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbSimpleState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;
import com.dododo.ariadne.jaxb.util.JaxbSingleFieldStateComparator;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContract;

import java.util.Collection;

public class JaxbCallToState implements JaxbSimpleState {

    private final JaxbSingleFieldStateComparator comparator;

    private final String value;

    public JaxbCallToState(String value) {
        this.comparator = new JaxbSingleFieldStateComparator();
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        ((RenPyJaxbFlowchartContract) contract).accept(this);
    }

    @Override
    public void accept(JaxbFlowchartMouseStrategy strategy, JaxbFlowchartContract callback, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates) {
        strategy.acceptSingleState(this, callback, grayStates, blackStates);
    }

    @Override
    public int compareTo(JaxbState o) {
        return comparator.compare(this, o);
    }

    @Override
    public String toString() {
        return String.format("%s(value='%s')", getClass().getSimpleName(), value);
    }
}
