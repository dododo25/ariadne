package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;
import com.dododo.ariadne.jaxb.util.JaxbNoFiledStateComparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

@XmlRootElement(name = "flowchart")
@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbRootState implements JaxbComplexState {

    @XmlTransient
    private final JaxbNoFiledStateComparator comparator;

    @XmlElements({
            @XmlElement(name = "text", type = JaxbText.class),
            @XmlElement(name = "reply", type = JaxbReply.class),
            @XmlElement(name = "menu", type = JaxbMenu.class),
            @XmlElement(name = "marker", type = JaxbMarker.class),
            @XmlElement(name = "goto", type = JaxbGoToState.class),
            @XmlElement(name = "switch", type = JaxbComplexSwitch.class),
            @XmlElement(name = "end", type = JaxbEndState.class)
    })
    private List<JaxbState> children;

    @XmlTransient
    private JaxbState root;

    public JaxbRootState() {
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
        state.setRoot(this);
    }

    @Override
    public void addChildAt(int index, JaxbState state) {
        children.add(index, state);
        state.setRoot(this);
    }

    @Override
    public void removeChild(JaxbState state) {
        children.remove(state);
        state.setRoot(null);
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
        strategy.acceptComplexState(this, callback, grayStates, blackStates);
    }

    @Override
    public int compareTo(JaxbState o) {
        return comparator.compare(this, o);
    }
}
