package com.dododo.ariadne.xml.jaxb.model;

import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbSwitchBranch implements JaxbComplexState, JaxbSimpleState {

    @XmlAttribute(name = "condition")
    private String value;

    @XmlElements({
            @XmlElement(name = "statement", type = JaxbStatement.class),
            @XmlElement(name = "marker", type = JaxbMarker.class),
            @XmlElement(name = "goto", type = JaxbGoToState.class),
            @XmlElement(name = "switch", type = JaxbComplexSwitch.class),
            @XmlElement(name = "end", type = JaxbEndState.class)
    })
    private final List<JaxbState> children;

    public JaxbSwitchBranch() {
        this(null);
    }

    public JaxbSwitchBranch(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public String getValue() {
        return value;
    }

    public int childrenCount() {
        return children.size();
    }

    public JaxbState childAt(int index) {
        return children.get(index);
    }

    public Stream<JaxbState> childrenStream() {
        return children.stream();
    }

    public void addChild(JaxbState state) {
        children.add(state);
    }

    public void removeChild(JaxbState state) {
        children.remove(state);
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public int compareTo(JaxbState o) {
        return o instanceof JaxbSwitchBranch && Objects.equals(((JaxbSwitchBranch) o).value, this.value) ? 0 : 1;
    }

    @Override
    public String toString() {
        if (value == null) {
            return String.format("%s(condition=null)", getClass().getSimpleName());
        }

        return String.format("%s(condition='%s')", getClass().getSimpleName(), value);
    }
}
