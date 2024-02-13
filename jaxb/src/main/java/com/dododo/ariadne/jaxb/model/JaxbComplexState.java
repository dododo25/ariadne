package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;
import com.dododo.ariadne.jaxb.comparator.JaxbNoFiledStateComparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class JaxbComplexState extends JaxbState {

    @XmlTransient
    private final Comparator<JaxbState> comparator;

    @XmlElements({
            @XmlElement(name = "text", type = JaxbText.class),
            @XmlElement(name = "reply", type = JaxbReply.class),
            @XmlElement(name = "menu", type = JaxbMenu.class),
            @XmlElement(name = "label", type = JaxbLabel.class),
            @XmlElement(name = "goto", type = JaxbGoToState.class),
            @XmlElement(name = "switch", type = JaxbComplexSwitch.class),
            @XmlElement(name = "end", type = JaxbEndState.class)
    })
    protected final List<JaxbState> children;

    protected JaxbComplexState() {
        this(new JaxbNoFiledStateComparator());
    }

    protected JaxbComplexState(Comparator<JaxbState> comparator) {
        this.comparator = comparator;
        this.children = new CopyOnWriteArrayList<>();
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
        if (state == null) {
            throw new NullPointerException();
        }

        children.add(state);
        state.setRoot(this);
    }

    public void addChildAt(int index, JaxbState state) {
        if (state == null) {
            throw new NullPointerException();
        }

        children.add(index, state);
        state.setRoot(this);
    }

    public void removeChild(JaxbState state) {
        children.remove(state);

        if (state != null) {
            state.setRoot(null);
        }
    }

    @Override
    public final void accept(JaxbFlowchartMouseStrategy strategy, JaxbFlowchartContract callback, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates) {
        strategy.acceptComplexState(this, callback, grayStates, blackStates);
    }

    @Override
    public int compareTo(JaxbState o) {
        return comparator.compare(this, o);
    }
}
