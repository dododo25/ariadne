package com.dododo.ariadne.jaxb.model;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbMenu extends JaxbComplexState {

    @XmlElement(name = "option", type = JaxbOption.class)
    private final List<JaxbState> options;

    public JaxbMenu() {
        super();
        this.options = new CopyOnWriteArrayList<>();
    }

    @Override
    public int childrenCount() {
        return options.size();
    }

    @Override
    public JaxbState childAt(int index) {
        return options.get(index);
    }

    @Override
    public Stream<JaxbState> childrenStream() {
        return options.stream();
    }

    @Override
    public void addChild(JaxbState state) {
        if (state == null) {
            throw new NullPointerException();
        }

        options.add(state);
        state.setRoot(this);
    }

    @Override
    public void addChildAt(int index, JaxbState state) {
        if (state == null) {
            throw new NullPointerException();
        }

        options.add(index, state);
        state.setRoot(this);
    }

    @Override
    public void removeChild(JaxbState state) {
        options.remove(state);

        if (state != null) {
            state.setRoot(null);
        }
    }

    @Override
    public void accept(JaxbFlowchartContract contract) {
        contract.accept(this);
    }
}
