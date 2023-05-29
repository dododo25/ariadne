package com.dododo.ariadne.renpy.jaxb.model;

import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class JaxbSwitchBranch implements JaxbComplexState, JaxbSimpleState {

    private final String value;

    private final List<JaxbState> children;

    public JaxbSwitchBranch(String value) {
        this.value = value;
        this.children = new ArrayList<>();
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
