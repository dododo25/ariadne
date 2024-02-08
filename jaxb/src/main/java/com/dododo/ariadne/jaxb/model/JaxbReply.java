package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.mouse.strategy.JaxbFlowchartMouseStrategy;
import com.dododo.ariadne.jaxb.util.NullableStringComparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbReply implements JaxbState {

    @XmlTransient
    private final NullableStringComparator comparator;

    @XmlAttribute(name = "character")
    private String character;

    @XmlAttribute(name = "line")
    private String line;

    @XmlTransient
    private JaxbState root;

    public JaxbReply() {
        this(null, null);
    }

    public JaxbReply(String character, String line) {
        this.comparator = new NullableStringComparator();
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
    public JaxbState getRoot() {
        return root;
    }

    @Override
    public void setRoot(JaxbState state) {
        this.root = state;
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public void accept(JaxbFlowchartMouseStrategy strategy, JaxbFlowchartContract callback, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates) {
        strategy.acceptSingleState(this, callback, grayStates, blackStates);
    }

    @Override
    public int compareTo(JaxbState o) {
        if (!(o instanceof JaxbReply)) {
            return 1;
        }

        int res = comparator.compare(this.character, ((JaxbReply) o).character);

        if (res == 0) {
            return res;
        }

        return comparator.compare(this.line, ((JaxbReply) o).line);
    }

    @Override
    public String toString() {
        if (character == null) {
            return String.format("%s(character=null, line='%s')", getClass().getSimpleName(), line);
        }

        return String.format("%s(character='%s'', line='%s')", getClass().getSimpleName(), character, line);
    }
}
