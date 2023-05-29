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
        return o instanceof Reply
                && Objects.equals(((Reply) o).character, this.character)
                && Objects.equals(((Reply) o).line, this.line) ? 0 : 1;
    }

    @Override
    public String toString() {
        if (character == null) {
            return String.format("%s(character=null, line='%s')", getClass().getSimpleName(), line);
        }

        return String.format("%s(character='%s', line='%s')", getClass().getSimpleName(), character, line);
    }
}
