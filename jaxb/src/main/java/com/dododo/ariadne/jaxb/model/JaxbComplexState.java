package com.dododo.ariadne.jaxb.model;

import java.util.stream.Stream;

public interface JaxbComplexState extends JaxbState {

    int childrenCount();

    JaxbState childAt(int index);

    Stream<JaxbState> childrenStream();

    void addChild(JaxbState state);

    void removeChild(JaxbState state);
}
