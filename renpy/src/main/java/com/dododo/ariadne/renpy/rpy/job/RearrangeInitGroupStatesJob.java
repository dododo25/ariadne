package com.dododo.ariadne.renpy.rpy.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContractAdapter;
import com.dododo.ariadne.renpy.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.renpy.jaxb.mouse.strategy.ParentFirstJaxbFlowchartMouseStrategy;

public final class RearrangeInitGroupStatesJob extends AbstractJob {

    private final JaxbState rootState;

    public RearrangeInitGroupStatesJob(JaxbState rootState) {
        this.rootState = rootState;
    }

    @Override
    public void run() {
        JaxbFlowchartContract contract = new JaxbFlowchartContractAdapter() {
            @Override
            public void accept(JaxbGroupState state) {
                int lastInitStateIndex = findLastInitStateIndex(state);

                for (int i = lastInitStateIndex; i < state.childrenCount(); i++) {
                    JaxbState child = state.childAt(i);

                    if (child instanceof JaxbInitGroupState) {
                        state.removeChild(child);
                        state.addChildAt(lastInitStateIndex, child);

                        lastInitStateIndex++;
                    }
                }
            }

            private int findLastInitStateIndex(JaxbComplexState state) {
                int result = 0;

                for (int i = 0; i < state.childrenCount(); i++) {
                    JaxbState child = state.childAt(i);

                    if (child instanceof JaxbInitGroupState) {
                        result++;
                    } else {
                        break;
                    }
                }

                return result;
            }
        };
        JaxbFlowchartMouse mouse = new JaxbFlowchartMouse(contract, new ParentFirstJaxbFlowchartMouseStrategy());

        rootState.accept(mouse);
    }
}
