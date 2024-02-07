package com.dododo.ariadne.common.job;

import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.ParentFirstFlowchartMouse;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;

public final class PrepareEndStateJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartMouse mouse = new ParentFirstFlowchartMouse();

        StateCollector<ChainState> leafChainStateCollector = new LeafChainStateCollector(mouse);
        State flowchart = getFlowchart();

        leafChainStateCollector.collect(flowchart)
                .forEach(leaf -> leaf.setNext(new EndPoint()));
        prepareFalseCheckSwitchLeafStates(flowchart, mouse);
    }

    private static void prepareFalseCheckSwitchLeafStates(State state, FlowchartMouse mouse) {
        FlowchartContract contract = new FlowchartContractAdapter() {
            @Override
            public void accept(Switch aSwitch) {
                if (aSwitch.getFalseBranch() == null) {
                    aSwitch.setFalseBranch(new EndPoint());
                }
            }
        };

        mouse.accept(state, contract);
    }
}
