package com.dododo.ariadne.core.model;

import com.dododo.ariadne.core.contract.FlowchartContract;

import java.util.Objects;

public final class Reply extends ChainState {

    private final String character;

    private final String line;

    public Reply(String character, String line) {
        this.character = character;
        this.line = Objects.requireNonNull(line);
    }

    public String getCharacter() {
        return character;
    }

    public String getLine() {
        return line;
    }

    @Override
    public void accept(FlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public int compareTo(State o) {
        return compareByValuesPair(o, s -> ((Reply) s).getCharacter(), s -> ((Reply) s).getLine());
    }

    @Override
    public String toString() {
        if (character == null) {
            return String.format("%s(character=null, line='%s')", getClass().getSimpleName(), line);
        }

        return String.format("%s(character='%s', line='%s')", getClass().getSimpleName(), character, line);
    }
}
