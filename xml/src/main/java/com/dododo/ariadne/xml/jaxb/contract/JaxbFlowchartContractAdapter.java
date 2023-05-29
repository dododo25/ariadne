package com.dododo.ariadne.xml.jaxb.contract;

import com.dododo.ariadne.xml.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.xml.jaxb.model.JaxbEndState;
import com.dododo.ariadne.xml.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.xml.jaxb.model.JaxbMarker;
import com.dododo.ariadne.xml.jaxb.model.JaxbPassState;
import com.dododo.ariadne.xml.jaxb.model.JaxbRootState;
import com.dododo.ariadne.xml.jaxb.model.JaxbStatement;
import com.dododo.ariadne.xml.jaxb.model.JaxbSwitchBranch;

public abstract class JaxbFlowchartContractAdapter implements JaxbFlowchartContract {

    @Override
    public void accept(JaxbRootState state) {}

    @Override
    public void accept(JaxbStatement statement) {}

    @Override
    public void accept(JaxbComplexSwitch complexSwitch) {}

    @Override
    public void accept(JaxbSwitchBranch switchBranch) {}

    @Override
    public void accept(JaxbMarker marker) {}

    @Override
    public void accept(JaxbGoToState state) {}

    @Override
    public void accept(JaxbPassState state) {}

    @Override
    public void accept(JaxbEndState state) {}
}
