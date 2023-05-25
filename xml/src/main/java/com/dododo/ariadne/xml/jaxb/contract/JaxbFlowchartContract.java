package com.dododo.ariadne.xml.jaxb.contract;


import com.dododo.ariadne.xml.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.xml.jaxb.model.JaxbEndState;
import com.dododo.ariadne.xml.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.xml.jaxb.model.JaxbMarker;
import com.dododo.ariadne.xml.jaxb.model.JaxbPassState;
import com.dododo.ariadne.xml.jaxb.model.JaxbRootState;
import com.dododo.ariadne.xml.jaxb.model.JaxbStatement;
import com.dododo.ariadne.xml.jaxb.model.JaxbSwitchBranch;

public interface JaxbFlowchartContract {

    void accept(JaxbRootState state);

    void accept(JaxbStatement statement);

    void accept(JaxbComplexSwitch complexSwitch);

    void accept(JaxbSwitchBranch switchBranch);

    void accept(JaxbMarker marker);

    void accept(JaxbGoToState state);

    void accept(JaxbPassState state);

    void accept(JaxbEndState state);

}
