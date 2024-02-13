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

public interface JaxbFlowchartContract {

    void accept(JaxbRootState state);

    void accept(JaxbText text);

    void accept(JaxbReply reply);

    void accept(JaxbMenu menu);

    void accept(JaxbOption option);

    void accept(JaxbComplexSwitch complexSwitch);

    void accept(JaxbSwitchBranch switchBranch);

    void accept(JaxbLabel label);

    void accept(JaxbGoToState state);

    void accept(JaxbPassState state);

    void accept(JaxbEndState state);

}
