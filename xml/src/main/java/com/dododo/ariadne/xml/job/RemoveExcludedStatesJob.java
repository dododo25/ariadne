package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.xml.jaxb.contract.JaxbFlowchartContractAdapter;
import com.dododo.ariadne.xml.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.xml.jaxb.model.JaxbRootState;
import com.dododo.ariadne.xml.jaxb.model.JaxbState;
import com.dododo.ariadne.xml.jaxb.model.JaxbStatement;
import com.dododo.ariadne.xml.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.xml.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.xml.jaxb.mouse.strategy.ParentFirstJaxbFlowchartMouseStrategy;

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
                        .filter(JaxbStatement.class::isInstance)
                        .map(JaxbStatement.class::cast)
                        .filter(c -> excluded.stream().anyMatch(regex -> c.getValue().matches(regex)))
                        .forEach(complexState::removeChild);
            }
        };
        JaxbFlowchartMouse mouse = new JaxbFlowchartMouse(callback, new ParentFirstJaxbFlowchartMouseStrategy());

        rootState.accept(mouse);
    }
}
