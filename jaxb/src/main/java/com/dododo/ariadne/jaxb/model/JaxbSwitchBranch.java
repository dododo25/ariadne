package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbSwitchBranch extends JaxbComposedState {

    @XmlAttribute(name = "condition")
    private String value;

    public JaxbSwitchBranch() {
        this(null);
    }

    public JaxbSwitchBranch(String value) {
        super(value);
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
    public String toString() {
        if (value == null) {
            return String.format("%s(condition=null)", getClass().getSimpleName());
        }

        return String.format("%s(condition='%s')", getClass().getSimpleName(), value);
    }
}
