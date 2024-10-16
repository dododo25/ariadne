package com.dododo.ariadne.renpy.contract;

import com.dododo.ariadne.extended.contract.ExtendedFlowchartContract;
import com.dododo.ariadne.renpy.model.CallToState;
import com.dododo.ariadne.renpy.model.VariableGroupComplexState;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;

public interface RenPyFlowchartContract extends ExtendedFlowchartContract {

    void accept(CallToState callState);

    void accept(LabelledGroupComplexState group);

    void accept(VariableGroupComplexState group);
}
