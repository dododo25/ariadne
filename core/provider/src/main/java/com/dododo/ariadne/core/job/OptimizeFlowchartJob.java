package com.dododo.ariadne.core.job;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.util.FlowchartManipulatorUtil;

import java.util.HashMap;
import java.util.Map;

public final class OptimizeFlowchartJob extends AbstractJob {

    @Override
    public void run() {
        Map<Switch, State> switchToStateMap = new HashMap<>();
        Map<Menu, State> menuToStateMap = new HashMap<>();

        FlowchartContract callback = new FlowchartContractAdapter() {
            @Override
            public void accept(Switch aSwitch) {
                State trueBranch = aSwitch.getTrueBranch();
                State falseBranch = aSwitch.getFalseBranch();

                if (trueBranch == falseBranch) {
                    if (aSwitch == trueBranch) {
                        switchToStateMap.put(aSwitch, new EndPoint());
                    } else {
                        switchToStateMap.put(aSwitch, trueBranch);
                    }
                } else if (aSwitch == trueBranch) {
                    switchToStateMap.put(aSwitch, falseBranch);
                } else if (aSwitch == falseBranch) {
                    switchToStateMap.put(aSwitch, trueBranch);
                }
            }

            @Override
            public void accept(Menu menu) {
                State child = menu.branchAt(0).getNext();

                boolean res = menu.branchesStream()
                        .skip(1)
                        .map(ChainState::getNext)
                        .allMatch(state -> state == child);

                if (!res) {
                    return;
                }

                if (child == menu) {
                    menuToStateMap.put(menu, new EndPoint());
                } else {
                    menuToStateMap.put(menu, child);
                }
            }
        };
        FlowchartMouse mouse = new FlowchartMouse();

        mouse.accept(getFlowchart(), callback);

        switchToStateMap.forEach(FlowchartManipulatorUtil::replace);
        menuToStateMap.forEach((menu, state) -> {
            FlowchartManipulatorUtil.replace(menu, state);

            menu.branchesStream()
                    .forEach(option -> option.getNext().removeRoot(option));
        });

        if (!switchToStateMap.isEmpty() || !menuToStateMap.isEmpty()) {
            run();
        }
    }
}
