package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContractAdapter;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbText;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.jaxb.mouse.strategy.ParentFirstJaxbFlowchartMouseStrategy;

import java.util.Set;

public final class RemoveExcludedStatesJob extends AbstractJob {

    private final JaxbState rootState;

    public RemoveExcludedStatesJob(JaxbState rootState) {
        this.rootState = rootState;
    }

    @Override
    public void run() {
        Set<String> excluded = getConfiguration().getExcluded();

        JaxbFlowchartContract callback = new JaxbFlowchartContractAdapter() {

            @Override
            public void accept(JaxbRootState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(JaxbSwitchBranch switchBranch) {
                acceptComplexState(switchBranch);
            }

            private void acceptComplexState(JaxbComplexState complexState) {
                complexState.childrenStream()
                        .filter(JaxbText.class::isInstance)
                        .map(JaxbText.class::cast)
                        .filter(c -> excluded.stream().anyMatch(regex -> c.getValue().matches(regex)))
                        .forEach(complexState::removeChild);
            }
        };
        JaxbFlowchartMouse mouse = new JaxbFlowchartMouse(callback, new ParentFirstJaxbFlowchartMouseStrategy());

        rootState.accept(mouse);
    }
}
