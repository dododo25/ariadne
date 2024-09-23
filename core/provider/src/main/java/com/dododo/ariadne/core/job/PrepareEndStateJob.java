package com.dododo.ariadne.core.job;

import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.Switch;

public final class PrepareEndStateJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<ChainState> leafCollector = new LeafChainStateCollector(new FlowchartMouse());

        leafCollector.collect(getFlowchart())
                .forEach(leaf -> leaf.setNext(new EndPoint()));

        prepareFalseCheckSwitchLeafStates();
    }

    private void prepareFalseCheckSwitchLeafStates() {
        FlowchartContract contract = new FlowchartContractAdapter() {
            @Override
            public void accept(Switch aSwitch) {
                if (aSwitch.getFalseBranch() == null) {
                    aSwitch.setFalseBranch(new EndPoint());
                }
            }
        };

        new FlowchartMouse().accept(getFlowchart(), contract);
    }
}
