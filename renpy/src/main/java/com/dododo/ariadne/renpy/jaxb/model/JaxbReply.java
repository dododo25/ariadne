package com.dododo.ariadne.renpy.jaxb.model;

import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContract;

import java.util.Objects;

public class JaxbReply implements JaxbState {

    private final String character;

    private final String line;

    public JaxbReply(String character, String line) {
        this.character = character;
        this.line = line;
    }

    public String getCharacter() {
        return character;
    }

    public String getLine() {
        return line;
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public int compareTo(JaxbState o) {
        return o instanceof JaxbReply && Objects.equals(((JaxbReply) o).line, this.line) ? 0 : 1;
    }

    @Override
    public String toString() {
        if (character == null) {
            return String.format("%s(character=null, line='%s')", getClass().getSimpleName(), line);
        }

        return String.format("%s(character='%s', line='%s')", getClass().getSimpleName(), character, line);
    }
}
