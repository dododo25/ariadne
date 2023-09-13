package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.contract.JaxbFlowchartContractAdapter;
import com.dododo.ariadne.renpy.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbOption;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import com.dododo.ariadne.renpy.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.renpy.jaxb.mouse.strategy.ParentFirstJaxbFlowchartMouseStrategy;

import java.util.stream.IntStream;

public final class PrepareJaxbComplexSwitchStatesJob extends AbstractJob {

    private final JaxbState rootState;

    public PrepareJaxbComplexSwitchStatesJob(JaxbState rootState) {
        this.rootState = rootState;
    }

    @Override
    public void run() {
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
        };

        JaxbFlowchartMouse mouse = new JaxbFlowchartMouse(callback, new ParentFirstJaxbFlowchartMouseStrategy());

        rootState.accept(mouse);
    }

    private void acceptComplexState(JaxbComplexState state) {
        int i = 0;
        Integer fromIndex = null;

        while (i < state.childrenCount()) {
            JaxbState child = state.childAt(i);

            if (fromIndex != null && !(child instanceof JaxbSwitchFalseBranch)) {
                process(state, fromIndex, i);

                i = fromIndex + 1;
                fromIndex = null;
            }

            if (child instanceof JaxbSwitchBranch) {
                fromIndex = i;
            }

            i++;
        }

        if (fromIndex != null) {
            process(state, fromIndex, state.childrenCount());
        }
    }

    private void process(JaxbComplexState state, int from, int to) {
        JaxbComplexSwitch complexSwitch = new JaxbComplexSwitch();

        IntStream.range(from, to).forEach(index -> {
            JaxbComplexState branch = (JaxbComplexState) state.childAt(from);

            complexSwitch.addChild(branch);
            state.removeChild(branch);
        });

        state.addChildAt(from, complexSwitch);
    }
}
