package com.dododo.ariadne.common.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.comparator.StateComparator;
import com.dododo.ariadne.core.mouse.ParentFirstFlowchartMouse;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.util.StateManipulatorUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class RemoveStateDuplicatesJob extends AbstractJob {

    private ParentFirstFlowchartMouse mouse;

    private StateComparator comparator;

    @Override
    public void run() {
        mouse = new ParentFirstFlowchartMouse();
        comparator = new StateComparator(mouse);

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
                .filter(s -> comparator.compare(key, s) == 0)
                .forEach(s -> StateManipulatorUtil.replace(s, key)));
    }

    private void removeOptionDuplicates() {
        Map<Menu, Set<Option>> map = new HashMap<>();

        FlowchartContract callback = new FlowchartContractAdapter() {
            @Override
            public void accept(Menu menu) {
                for (int i = 0; i < menu.branchesCount() - 1; i++) {
                    Option option = menu.branchAt(i);

                    for (int j = i + 1; j < menu.branchesCount(); j++) {
                        Option nextOption = menu.branchAt(j);

                        if (comparator.compare(option, nextOption) == 0) {
                            map.computeIfAbsent(menu, m -> new HashSet<>()).add(nextOption);
                        }
                    }
                }
            }
        };

        mouse.accept(getFlowchart(), callback);

        map.forEach((key, value) -> value.forEach(key::removeBranch));
    }
}
