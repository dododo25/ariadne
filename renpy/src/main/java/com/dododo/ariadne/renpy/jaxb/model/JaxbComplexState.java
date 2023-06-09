package com.dododo.ariadne.renpy.jaxb.model;

import java.util.stream.Stream;

public interface JaxbComplexState extends JaxbState {

    int childrenCount();

    JaxbState childAt(int index);

    Stream<JaxbState> childrenStream();

    void addChild(JaxbState state);

    void addChildAt(int index, JaxbState state);

    void removeChild(JaxbState state);
}
