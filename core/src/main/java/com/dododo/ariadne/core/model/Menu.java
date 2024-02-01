package com.dododo.ariadne.core.model;

import com.dododo.ariadne.core.contract.FlowchartContract;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class Menu extends State {

    private final String value;

    private final List<Option> branches;

    public Menu() {
        this(null);
    }

    public Menu(String value) {
        this.value = value;
        this.branches = new ArrayList<>();
    }

    public String getValue() {
        return value;
    }

    public int branchesCount() {
        return branches.size();
    }

    public Option branchAt(int index) {
        return branches.get(index);
    }

    public Stream<Option> branchesStream() {
        return branches.stream();
    }

    public void addBranch(Option option) {
        branches.add(option);
        option.addRoot(this);
    }

    public void removeBranch(Option option) {
        branches.remove(option);
        option.removeRoot(this);
    }

    @Override
    public void accept(FlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public int compareTo(State o) {
        return compareBySingleValue(o, s -> ((Menu) s).getValue());
    }

    @Override
    public String toString() {
        if (value == null) {
            return String.format("%s(value=null)", getClass().getSimpleName());
        }

        return String.format("%s(value='%s')", getClass().getSimpleName(), value);
    }
}
