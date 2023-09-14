package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.util.JaxbNoFiledStateComparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbComplexSwitch implements JaxbComplexState {

    @XmlTransient
    private final JaxbNoFiledStateComparator comparator;

    @XmlElement(name = "if", type = JaxbSwitchBranch.class)
    private JaxbState ifChild;

    @XmlElement(name = "else-if", type = JaxbSwitchBranch.class)
    private final List<JaxbState> elseIfChildren;

    @XmlElement(name = "else", type = JaxbSwitchBranch.class)
    private final List<JaxbState> elseChildren;

    public JaxbComplexSwitch() {
        this.comparator = new JaxbNoFiledStateComparator();
        this.elseIfChildren = new CopyOnWriteArrayList<>();
        this.elseChildren = new CopyOnWriteArrayList<>();
    }

    @Override
    public int childrenCount() {
        return elseIfChildren.size() + elseChildren.size() + (ifChild == null ? 0 : 1);
    }

    @Override
    public JaxbState childAt(int index) {
        if (index == 0) {
            return ifChild;
        } else if (index < elseIfChildren.size() + 1) {
            return elseIfChildren.get(index - 1);
        } else {
            return elseChildren.get(index - elseIfChildren.size() - 1);
        }
    }

    @Override
    public Stream<JaxbState> childrenStream() {
        return Stream.concat(Stream.of(ifChild), Stream.concat(elseIfChildren.stream(), elseChildren.stream()));
    }

    @Override
    public void addChild(JaxbState state) {
        if (!(state instanceof JaxbSwitchBranch)) {
            throw new IllegalArgumentException();
        }

        JaxbSwitchBranch branch = (JaxbSwitchBranch) state;

        if (ifChild == null) {
            ifChild = branch;
        } else if (branch.getValue() == null) {
            elseChildren.add(state);
        } else {
            elseIfChildren.add(state);
        }
    }

    @Override
    public void removeChild(JaxbState state) {
        if (ifChild == state) {
            ifChild = null;
        } else {
            elseIfChildren.remove(state);
            elseChildren.remove(state);
        }
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public int compareTo(JaxbState o) {
        return comparator.compare(this, o);
    }
}
