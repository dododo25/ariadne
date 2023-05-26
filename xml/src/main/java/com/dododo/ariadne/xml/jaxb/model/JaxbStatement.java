package com.dododo.ariadne.xml.jaxb.model;

import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbStatement implements JaxbSimpleState {

    @XmlAttribute
    private String value;

    public JaxbStatement() {}

    public JaxbStatement(String value) {
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
        return o instanceof JaxbStatement && Objects.equals(((JaxbStatement) o).value, this.value) ? 0 : 1;
    }

    @Override
    public String toString() {
        return String.format("%s(condition='%s')", getClass().getSimpleName(), value);
    }
}
