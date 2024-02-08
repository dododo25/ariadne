package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;
import com.dododo.ariadne.jaxb.util.JaxbSingleFieldStateComparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbLabel implements JaxbSimpleState {

    @XmlTransient
    private final JaxbSingleFieldStateComparator comparator;

    @XmlAttribute
    private String value;

    @XmlTransient
    private JaxbState root;

    public JaxbLabel() {
        this(null);
    }

    public JaxbLabel(String value) {
        this.comparator = new JaxbSingleFieldStateComparator();
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
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

    @Override
    public String toString() {
        return String.format("%s(value='%s')", getClass().getSimpleName(), value);
    }
}
