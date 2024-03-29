package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.util.JaxbSingleFieldStateComparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbSwitchBranch implements JaxbComplexState, JaxbSimpleState {

    @XmlTransient
    protected final JaxbSingleFieldStateComparator comparator;

    @XmlAttribute(name = "condition")
    protected String value;

    @XmlElements({
            @XmlElement(name = "text", type = JaxbText.class),
            @XmlElement(name = "reply", type = JaxbReply.class),
            @XmlElement(name = "menu", type = JaxbMenu.class),
            @XmlElement(name = "marker", type = JaxbMarker.class),
            @XmlElement(name = "goto", type = JaxbGoToState.class),
            @XmlElement(name = "switch", type = JaxbComplexSwitch.class),
            @XmlElement(name = "end", type = JaxbEndState.class)
    })
    protected final List<JaxbState> children;

    public JaxbSwitchBranch() {
        this(null);
    }

    public JaxbSwitchBranch(String value) {
        this.comparator = new JaxbSingleFieldStateComparator();
        this.value = value;
        this.children = new CopyOnWriteArrayList<>();
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public int childrenCount() {
        return children.size();
    }

    @Override
    public JaxbState childAt(int index) {
        return children.get(index);
    }

    @Override
    public Stream<JaxbState> childrenStream() {
        return children.stream();
    }

    @Override
    public void addChild(JaxbState state) {
        children.add(state);
    }

    @Override
    public void removeChild(JaxbState state) {
        children.remove(state);
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
        if (value == null) {
            return String.format("%s(condition=null)", getClass().getSimpleName());
        }

        return String.format("%s(condition='%s')", getClass().getSimpleName(), value);
    }
}
