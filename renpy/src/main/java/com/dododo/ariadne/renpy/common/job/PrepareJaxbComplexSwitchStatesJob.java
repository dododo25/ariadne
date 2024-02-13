package com.dododo.ariadne.renpy.common.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContractAdapter;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import com.dododo.ariadne.renpy.jaxb.mouse.ParentFirstRenPyJaxbFlowchartMouse;

import java.util.stream.IntStream;

public final class PrepareJaxbComplexSwitchStatesJob extends AbstractJob {

    private final JaxbState rootState;

    public PrepareJaxbComplexSwitchStatesJob(JaxbState rootState) {
        this.rootState = rootState;
    }

    @Override
    public void run() {
        RenPyJaxbFlowchartContract callback = new RenPyJaxbFlowchartContractAdapter() {

            @Override
            public void accept(JaxbRootState state) {
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
        JaxbFlowchartMouse mouse = new ParentFirstRenPyJaxbFlowchartMouse();

        mouse.accept(rootState, callback);
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

            if (child instanceof JaxbSwitchBranch && !(child instanceof JaxbSwitchFalseBranch)) {
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

        state.addChildAt(from, complexSwitch);

        IntStream.range(from, to).forEach(index -> {
            JaxbComplexState branch = (JaxbComplexState) state.childAt(from + 1);

            state.removeChild(branch);
            complexSwitch.addChild(branch);
        });
    }
}
