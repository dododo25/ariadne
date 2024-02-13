package com.dododo.ariadne.jaxb.contract;

import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbEndState;
import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.jaxb.model.JaxbLabel;
import com.dododo.ariadne.jaxb.model.JaxbMenu;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbReply;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.model.JaxbText;

public abstract class JaxbFlowchartContractAdapter implements JaxbFlowchartContract {

    @Override
    public void accept(JaxbRootState state) {}

    @Override
    public void accept(JaxbText text) {}

    @Override
    public void accept(JaxbReply reply) {}

    @Override
    public void accept(JaxbMenu menu) {}

    @Override
    public void accept(JaxbOption option) {}

    @Override
    public void accept(JaxbComplexSwitch complexSwitch) {}

    @Override
    public void accept(JaxbSwitchBranch switchBranch) {}

    @Override
    public void accept(JaxbLabel label) {}

    @Override
    public void accept(JaxbGoToState state) {}

    @Override
    public void accept(JaxbPassState state) {}

    @Override
    public void accept(JaxbEndState state) {}
}
