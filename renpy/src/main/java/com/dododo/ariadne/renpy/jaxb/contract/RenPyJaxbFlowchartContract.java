package com.dododo.ariadne.renpy.jaxb.contract;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.model.JaxbCallToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbRenPyMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;

public interface RenPyJaxbFlowchartContract extends JaxbFlowchartContract {

    void accept(JaxbInitGroupState state);

    void accept(JaxbRenPyMenu menu);

    void accept(JaxbSwitchFalseBranch switchBranch);

    void accept(JaxbLabelledGroup group);

    void accept(JaxbCallToState state);

}
