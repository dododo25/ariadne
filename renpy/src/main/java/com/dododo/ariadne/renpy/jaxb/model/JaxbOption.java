package com.dododo.ariadne.renpy.jaxb.model;

import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class JaxbOption implements JaxbComplexState {

    private final String value;

    private final String condition;

    private final List<JaxbState> children;

    public JaxbOption(String value, String condition) {
        this.value = value;
        this.condition = condition;
        this.children = new ArrayList<>();
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
    public int compareTo(JaxbState o) {
        return o instanceof JaxbOption && Objects.equals(((JaxbOption) o).value, this.value) ? 0 : 1;
    }

    @Override
    public String toString() {
        if (value == null) {
            return String.format("%s(value='%s', condition=null)", getClass().getSimpleName(), value);
        }

        return String.format("%s(value='%s', condition='%s')", getClass().getSimpleName(), value, condition);
    }
}
