package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.util.JaxbNoFiledStateComparator;

import javax.xml.bind.annotation.XmlTransient;

public class JaxbPassState implements JaxbState {

    @XmlTransient
    private final JaxbNoFiledStateComparator comparator;

    public JaxbPassState() {
        comparator = new JaxbNoFiledStateComparator();
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public int compareTo(JaxbState o) {
        return comparator.compare(this, o);
    }
}
