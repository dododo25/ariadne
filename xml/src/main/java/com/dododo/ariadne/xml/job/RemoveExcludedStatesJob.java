package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.xml.contract.XmlFlowchartContractAdapter;
import com.dododo.ariadne.xml.model.ComplexState;
import com.dododo.ariadne.xml.model.ComplexSwitchBranch;
import com.dododo.ariadne.xml.mouse.XmlFlowchartMouse;

import java.util.Set;

public final class RemoveExcludedStatesJob extends AbstractJob {

    @Override
    public void run() {
        Set<String> excluded = getConfiguration().getExcluded();

        FlowchartContract callback = new XmlFlowchartContractAdapter() {

            @Override
            public void accept(ComplexState state) {
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
        FlowchartMouse mouse = new XmlFlowchartMouse();

        mouse.accept(getFlowchart(), callback);
    }
}
