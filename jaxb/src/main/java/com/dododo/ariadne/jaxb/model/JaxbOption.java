package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;
import com.dododo.ariadne.jaxb.util.NullableStringComparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbOption implements JaxbComplexState {

    @XmlTransient
    private final NullableStringComparator comparator;

    @XmlAttribute
    private String value;

    @XmlAttribute
    private String condition;

    @XmlElements({
            @XmlElement(name = "text", type = JaxbText.class),
            @XmlElement(name = "reply", type = JaxbReply.class),
            @XmlElement(name = "menu", type = JaxbMenu.class),
            @XmlElement(name = "label", type = JaxbLabel.class),
            @XmlElement(name = "goto", type = JaxbGoToState.class),
            @XmlElement(name = "switch", type = JaxbComplexSwitch.class),
            @XmlElement(name = "end", type = JaxbEndState.class)
    })
    private final List<JaxbState> children;

    public JaxbOption() {
        this(null, null);
    }

    public JaxbOption(String value, String condition) {
        this.comparator = new NullableStringComparator();
        this.value = value;
        this.condition = condition;
        this.children = new CopyOnWriteArrayList<>();
    }

    public String getValue() {
        return value;
    }

    public String getCondition() {
        return condition;
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
        if (!(o instanceof JaxbOption)) {
            return 1;
        }

        int res = comparator.compare(this.value, ((JaxbOption) o).value);

        if (res == 0) {
            return res;
        }

        return comparator.compare(this.condition, ((JaxbOption) o).condition);
    }

    @Override
    public String toString() {
        if (condition == null) {
            return String.format("%s(value='%s')", getClass().getSimpleName(), value);
        }

        return String.format("%s(value='%s', condition='%s')", getClass().getSimpleName(), value, condition);
    }
}
