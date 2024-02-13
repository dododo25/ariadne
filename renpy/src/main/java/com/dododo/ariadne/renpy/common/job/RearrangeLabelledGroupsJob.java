package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContractAdapter;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbRenPyMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import com.dododo.ariadne.renpy.jaxb.mouse.ChildFirstRenPyJaxbFlowchartMouse;

import java.util.stream.Collectors;

public final class RearrangeLabelledGroupsJob extends AbstractJob {

    private final JaxbState rootState;

    public RearrangeLabelledGroupsJob(JaxbState rootState) {
        this.rootState = rootState;
    }

    @Override
    public void run() {
        RenPyJaxbFlowchartContract callback = new RenPyJaxbFlowchartContractAdapter() {

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

            private void acceptComplexState(JaxbComplexState state) {
                int i = 0;

                while (i < state.childrenCount()) {
                    JaxbState child = state.childAt(i);

                    if (child instanceof JaxbLabelledGroup) {
                        state.removeChild(child);
                        ((JaxbComplexState) rootState).addChild(child);
                    } else {
                        i++;
                    }
                }

                if (state.childrenCount() == 0) {
                    state.addChild(new JaxbPassState());
                }
            }
        };

        forEachSecondLevelStates(callback);
    }

    private void forEachSecondLevelStates(JaxbFlowchartContract callback) {
        JaxbFlowchartMouse mouse = new ChildFirstRenPyJaxbFlowchartMouse();

        JaxbFlowchartContract innerCallback = new RenPyJaxbFlowchartContractAdapter() {

            @Override
            public void accept(JaxbLabelledGroup group) {
                acceptComplexState(group);
            }

            @Override
            public void accept(JaxbRenPyMenu menu) {
                acceptComplexState(menu);
            }

            @Override
            public void accept(JaxbComplexSwitch complexSwitch) {
                acceptComplexState(complexSwitch);
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
                complexState.childrenStream()
                        .forEach(child -> mouse.accept(child, callback));
            }
        };

        ((JaxbComplexState) rootState).childrenStream()
                .collect(Collectors.toList())
                .forEach(state -> state.accept(innerCallback));
    }
}
