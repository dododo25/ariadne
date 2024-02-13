package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;
import com.dododo.ariadne.jaxb.comparator.JaxbSimpleStateComparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class JaxbSimpleState extends JaxbState {

    @XmlTransient
    private final JaxbSimpleStateComparator comparator;

    @XmlAttribute
    private String value;

    protected JaxbSimpleState() {
        this(null);
    }

    protected JaxbSimpleState(String value) {
        this.comparator = new JaxbSimpleStateComparator();
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public final void accept(JaxbFlowchartMouseStrategy strategy, JaxbFlowchartContract callback, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates) {
        strategy.acceptSingleState(this, callback, grayStates, blackStates);
    }

    @Override
    public final int compareTo(JaxbState o) {
        return comparator.compare(this, o);
    }

    @Override
    public final String toString() {
        return String.format("%s(value='%s')", getClass().getSimpleName(), value);
    }
}
