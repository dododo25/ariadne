package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.common.exception.AriadneException;
import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;
import com.dododo.ariadne.jaxb.util.JaxbNoFiledStateComparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
    private JaxbState elseChild;

    public JaxbComplexSwitch() {
        this.comparator = new JaxbNoFiledStateComparator();
        this.elseIfChildren = new CopyOnWriteArrayList<>();
    }

    @Override
    public int childrenCount() {
        return elseIfChildren.size() + (ifChild == null ? 0 : 1) + (elseChild == null ? 0 : 1);
    }

    @Override
    public JaxbState childAt(int index) {
        if (index < 0 || index >= childrenCount()) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            return ifChild;
        } else if (index < elseIfChildren.size() + 1) {
            return elseIfChildren.get(index - 1);
        } else {
            return elseChild;
        }
    }

    @Override
    public Stream<JaxbState> childrenStream() {
        return Stream.concat(Stream.of(ifChild), Stream.concat(elseIfChildren.stream(), Stream.of(elseChild)))
                .filter(Objects::nonNull);
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
            if (elseChild != null) {
                throw new AriadneException("Illegal object insertion");
            }
            elseChild = branch;
        } else {
            elseIfChildren.add(state);
        }
    }

    @Override
    public void addChildAt(int index, JaxbState state) {
        if (!(state instanceof JaxbSwitchBranch)) {
            throw new IllegalArgumentException();
        }

        if (index < 0 || index >= childrenCount()) {
            throw new IndexOutOfBoundsException();
        }

        JaxbSwitchBranch branch = (JaxbSwitchBranch) state;

        if (index == 0) {
            ifChild = branch;
        } else if (index < elseIfChildren.size() + 1) {
            elseIfChildren.set(index - 1, branch);
        } else {
            elseChild = branch;
        }
    }

    @Override
    public void removeChild(JaxbState state) {
        if (ifChild == state) {
            ifChild = null;
        } else if (elseChild == state) {
            elseChild = null;
        } else {
            elseIfChildren.remove(state);
        }
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
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
