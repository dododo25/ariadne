package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.LeafChainStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContract;
import com.dododo.ariadne.xml.common.contract.XmlFlowchartContractAdapter;
import com.dododo.ariadne.xml.common.factory.XmlFlowchartMouseFactory;
import com.dododo.ariadne.xml.common.mouse.XmlFlowchartMouse;
import com.dododo.ariadne.xml.common.mouse.strategy.ParentFirstXmlFlowchartMouseStrategy;

public final class PrepareEndStateJob extends AbstractJob {

    @Override
    public void run() {
        StateCollector<ChainState> leafChainStateCollector
                = new LeafChainStateCollector(new XmlFlowchartMouseFactory());
        State flowchart = getFlowchart();

        leafChainStateCollector.collect(flowchart)
                .forEach(leaf -> leaf.setNext(new EndPoint()));
        prepareFalseCheckSwitchLeafStates(flowchart);
    }

    private static void prepareFalseCheckSwitchLeafStates(State state) {
        XmlFlowchartContract contract = new XmlFlowchartContractAdapter() {
            @Override
            public void accept(Switch aSwitch) {
                if (aSwitch.getFalseBranch() == null) {
                    aSwitch.setFalseBranch(new EndPoint());
                }
            }
        };

        XmlFlowchartMouse mouse = new XmlFlowchartMouse(contract, new ParentFirstXmlFlowchartMouseStrategy());
        state.accept(mouse);
    }
}
