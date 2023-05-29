package com.dododo.ariadne.common.job;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.strategy.ChildFirstFlowchartMouseStrategy;
import com.dododo.ariadne.core.mouse.strategy.ParentFirstFlowchartMouseStrategy;
import com.dododo.ariadne.core.util.StateManipulatorUtil;

import java.util.concurrent.atomic.AtomicReference;

public final class OptimizeFlowchartJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartContract contract = new FlowchartContractAdapter() {
            @Override
            public void accept(Switch aSwitch) {
                State trueBranch = aSwitch.getTrueBranch();
                State falseBranch = aSwitch.getFalseBranch();

                if (trueBranch == falseBranch) {
                    if (aSwitch == trueBranch) {
                        StateManipulatorUtil.replace(aSwitch, new EndPoint());
                    } else {
                        StateManipulatorUtil.replace(aSwitch, trueBranch);
                    }
                } else if (aSwitch == trueBranch) {
                    StateManipulatorUtil.replace(aSwitch, falseBranch);
                } else if (aSwitch == falseBranch) {
                    StateManipulatorUtil.replace(aSwitch, trueBranch);
                }
            }

            @Override
            public void accept(Menu menu) {
                AtomicReference<State> ref = new AtomicReference<>(menu.branchAt(0).getNext());

                if (menu.branchesStream().skip(1).map(ChainState::getNext)
                        .allMatch(state -> state == ref.get())) {
                    if (ref.get() == menu) {
                        ref.set(new EndPoint());
                    }

                    StateManipulatorUtil.replace(menu, ref.get());

                    menu.branchesStream().forEach(option -> {
                        FlowchartContract c = new SimpleFlowchartContract() {
                            @Override
                            public void acceptState(State state) {
                                state.removeRoot(option);
                            }
                        };
                        FlowchartMouse mouse = new FlowchartMouse(c, new ParentFirstFlowchartMouseStrategy());

                        getFlowchart().accept(mouse);
                    });
                }
            }
        };

        FlowchartMouse mouse = new FlowchartMouse(contract, new ChildFirstFlowchartMouseStrategy());
        getFlowchart().accept(mouse);
    }
}
