package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContractAdapter;
import com.dododo.ariadne.renpy.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbOption;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import com.dododo.ariadne.renpy.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.renpy.jaxb.mouse.strategy.ChildFirstJaxbFlowchartMouseStrategy;

import java.util.stream.Collectors;

public final class RearrangeLabelledGroupsJob extends AbstractJob {

    private final JaxbState rootState;

    public RearrangeLabelledGroupsJob(JaxbState rootState) {
        this.rootState = rootState;
    }

    @Override
    public void run() {
        JaxbComplexState rootComplexState = (JaxbComplexState) rootState;

        JaxbFlowchartContract callback = new JaxbFlowchartContractAdapter() {
            @Override
            public void accept(JaxbGroupState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(JaxbInitGroupState state) {
                acceptComplexState(state);
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

            @Override
            public void accept(JaxbLabelledGroup group) {
                acceptComplexState(group);
            }

            private void acceptComplexState(JaxbComplexState state) {
                int i = 0;

                while (i < state.childrenCount()) {
                    JaxbState child = state.childAt(i);

                    if (child instanceof JaxbLabelledGroup && !isPartOfRootState(child)) {
                        state.removeChild(child);
                        ((JaxbComplexState) rootState).addChild(child);
                    } else {
                        i++;
                    }
                }
            }

            private boolean isPartOfRootState(JaxbState state) {
                return rootComplexState.childrenStream().anyMatch(child -> child == state);
            }
        };
        JaxbFlowchartMouse mouse = new JaxbFlowchartMouse(callback, new ChildFirstJaxbFlowchartMouseStrategy());

        rootComplexState.childrenStream()
                .collect(Collectors.toList())
                .forEach(state -> state.accept(mouse));
    }
}
