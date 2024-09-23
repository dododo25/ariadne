package com.dododo.ariadne.core.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.util.FlowchartManipulatorUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class RemoveStateDuplicatesJob extends AbstractJob {

    private FlowchartMouse mouse;

    @Override
    public void run() {
        this.mouse = new FlowchartMouse();

        removeChainStateDuplicates();
        removeOptionDuplicates();
    }

    private void removeChainStateDuplicates() {
        Map<State, Set<State>> map = new HashMap<>();

        StateCollector<ChainState> chainStateCollector = new GenericStateCollector<>(mouse, ChainState.class);

        List<ChainState> states = chainStateCollector.collect(getFlowchart())
                .stream()
                .filter(state -> !(state instanceof Option))
                .collect(Collectors.toList());

        while (!states.isEmpty()) {
            State s1 = states.remove(0);

            int i = 0;

            while (i < states.size()) {
                State s2 = states.get(i);

                if (s1 != s2 && s1.compareTo(s2) == 0) {
                    map.computeIfAbsent(s1, m -> new HashSet<>()).add(s2);
                    states.remove(i);
                } else {
                    i++;
                }
            }
        }

        map.forEach((key, value) -> value.stream()
                .filter(s -> isEqual(key, s))
                .forEach(s -> FlowchartManipulatorUtil.replace(s, key)));
    }

    private void removeOptionDuplicates() {
        Map<Menu, Set<Option>> map = new HashMap<>();

        FlowchartContract callback = new FlowchartContractAdapter() {
            @Override
            public void accept(Menu menu) {
                for (int i = 0; i < menu.branchesCount() - 1; i++) {
                    Option option = menu.branchAt(i);

                    for (int j = i + 1; j < menu.branchesCount(); j++) {
                        Option anotherOption = menu.branchAt(j);

                        if (isEqual(option, anotherOption)) {
                            map.computeIfAbsent(menu, m -> new HashSet<>()).add(anotherOption);
                        }
                    }
                }
            }
        };

        mouse.accept(getFlowchart(), callback);
        map.forEach((key, value) -> value.forEach(key::removeBranch));
    }

    private boolean isEqual(State s1, State s2) {
        if (s1 instanceof ChainState && s2 instanceof ChainState) {
            return ((ChainState) s1).getNext() == ((ChainState) s2).getNext();
        } else if (s1 instanceof Switch && s2 instanceof Switch) {
            return ((Switch) s1).getTrueBranch() == ((Switch) s2).getTrueBranch()
                    && ((Switch) s1).getFalseBranch() == ((Switch) s2).getFalseBranch();
        }

        return false;
    }
}
