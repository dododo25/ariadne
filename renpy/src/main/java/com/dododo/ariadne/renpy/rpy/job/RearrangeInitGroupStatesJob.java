package com.dododo.ariadne.renpy.rpy.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContractAdapter;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import com.dododo.ariadne.renpy.jaxb.mouse.ParentFirstRenPyJaxbFlowchartMouse;

public final class RearrangeInitGroupStatesJob extends AbstractJob {

    private final JaxbState rootState;

    private int lastInitStateIndex;

    public RearrangeInitGroupStatesJob(JaxbState rootState) {
        this.rootState = rootState;
    }

    @Override
    public void run() {
        lastInitStateIndex = 0;

        RenPyJaxbFlowchartContract callback = new RenPyJaxbFlowchartContractAdapter() {

            @Override
            public void accept(JaxbRootState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(JaxbLabelledGroup group) {
                acceptComplexState(group);
            }

            @Override
            public void accept(JaxbOption option) {
                acceptComplexState(option);
            }

            @Override
            public void accept(JaxbSwitchBranch switchBranch) {
                acceptComplexState(switchBranch);
            }

            @Override
            public void accept(JaxbSwitchFalseBranch switchBranch) {
                acceptComplexState(switchBranch);
            }

            private void acceptComplexState(JaxbComplexState complexState) {
                int i = 0;

                while (i < complexState.childrenCount()) {
                    JaxbState child = complexState.childAt(i);

                    if (child instanceof JaxbInitGroupState) {
                        complexState.removeChild(child);
                        ((JaxbComplexState) rootState).addChildAt(lastInitStateIndex++, child);
                    }

                    if (!(child instanceof JaxbInitGroupState) || complexState == rootState) {
                        i++;
                    }
                }

                if (complexState.childrenCount() == 0) {
                    complexState.addChild(new JaxbPassState());
                }
            }
        };
        JaxbFlowchartMouse mouse = new ParentFirstRenPyJaxbFlowchartMouse();

        mouse.accept(rootState, callback);
    }
}
