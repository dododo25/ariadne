package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.util.JaxbSingleFieldStateComparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbGoToState implements JaxbSimpleState {

    @XmlTransient
    private final JaxbSingleFieldStateComparator comparator;

    @XmlAttribute
    private String value;

    public JaxbGoToState() {
        this(null);
    }

    public JaxbGoToState(String value) {
        this.comparator = new JaxbSingleFieldStateComparator();
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
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
