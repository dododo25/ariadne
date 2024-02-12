package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;
import com.dododo.ariadne.jaxb.util.JaxbNoFiledStateComparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbMenu implements JaxbComplexState {

    @XmlTransient
    private final JaxbNoFiledStateComparator comparator;

    @XmlElement(name = "option", type = JaxbOption.class)
    private final List<JaxbState> children;

    public JaxbMenu() {
        this.comparator = new JaxbNoFiledStateComparator();
        this.children = new CopyOnWriteArrayList<>();
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
    public void addChildAt(int index, JaxbState state) {
        children.add(index, state);
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
    public void accept(JaxbFlowchartMouseStrategy strategy, JaxbFlowchartContract callback, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates) {
        strategy.acceptComplexState(this, callback, grayStates, blackStates);
    }

    @Override
    public int compareTo(JaxbState o) {
        return comparator.compare(this, o);
    }
}
