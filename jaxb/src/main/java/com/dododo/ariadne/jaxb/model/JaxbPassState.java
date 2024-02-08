package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;
import com.dododo.ariadne.jaxb.util.JaxbNoFiledStateComparator;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

public class JaxbPassState implements JaxbState {

    @XmlTransient
    private final JaxbNoFiledStateComparator comparator;

    @XmlTransient
    private JaxbState root;

    public JaxbPassState() {
        comparator = new JaxbNoFiledStateComparator();
    }

    @Override
    public JaxbState getRoot() {
        return root;
    }

    @Override
    public void setRoot(JaxbState state) {
        this.root = state;
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public void accept(JaxbFlowchartMouseStrategy strategy, JaxbFlowchartContract callback, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates) {
        strategy.acceptSingleState(this, callback, grayStates, blackStates);
    }

    @Override
    public int compareTo(JaxbState o) {
        return comparator.compare(this, o);
    }
}
