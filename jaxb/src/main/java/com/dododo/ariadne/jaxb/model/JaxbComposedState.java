package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.comparator.JaxbComposedStateComparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class JaxbComposedState extends JaxbComplexState {

    @XmlAttribute
    private final String value;

    protected JaxbComposedState() {
        this(null);
    }

    protected JaxbComposedState(String value) {
        super(new JaxbComposedStateComparator());
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value == null) {
            return String.format("%s(value=null)", getClass().getSimpleName());
        }

        return String.format("%s(value='%s')", getClass().getSimpleName(), value);
    }
}
