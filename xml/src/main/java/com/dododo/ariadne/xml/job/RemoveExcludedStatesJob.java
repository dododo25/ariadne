package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.core.job.AbstractJob;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.contract.ExtendedFlowchartContractAdapter;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.RootComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.mouse.ExtendedFlowchartMouse;

import java.util.Set;

public final class RemoveExcludedStatesJob extends AbstractJob {

    @Override
    public void run() {
        Set<String> excluded = getConfiguration().getExcluded();

        FlowchartContract callback = new ExtendedFlowchartContractAdapter() {

            @Override
            public void accept(RootComplexState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(ComplexSwitchBranch switchBranch) {
                acceptComplexState(switchBranch);
            }

            private void acceptComplexState(ComplexState complexState) {
                complexState.childrenStream()
                        .filter(Text.class::isInstance)
                        .map(Text.class::cast)
                        .filter(c -> excluded.stream().anyMatch(regex -> c.getValue().matches(regex)))
                        .forEach(complexState::removeChild);
            }
        };
        FlowchartMouse mouse = new ExtendedFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
