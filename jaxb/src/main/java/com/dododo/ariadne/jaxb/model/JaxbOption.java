package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.util.comparator.NullableStringComparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbOption extends JaxbComplexState {

    @XmlTransient
    private final NullableStringComparator stringComparator;

    @XmlAttribute
    private String value;

    @XmlAttribute
    private String condition;

    public JaxbOption() {
        this(null, null);
    }

    public JaxbOption(String value, String condition) {
        super();

        this.stringComparator = new NullableStringComparator();
        this.value = value;
        this.condition = condition;
    }

    public String getValue() {
        return value;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public int compareTo(JaxbState o) {
        if (!(o instanceof JaxbOption)) {
            return 1;
        }

        int res = stringComparator.compare(this.value, ((JaxbOption) o).value);

        if (res == 0) {
            return res;
        }

        return stringComparator.compare(this.condition, ((JaxbOption) o).condition);
    }

    @Override
    public String toString() {
        if (condition == null) {
            return String.format("%s(value='%s')", getClass().getSimpleName(), value);
        }

        return String.format("%s(value='%s', condition='%s')", getClass().getSimpleName(), value, condition);
    }
}
