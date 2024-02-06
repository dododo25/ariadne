package com.dododo.ariadne.common.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.comparator.StateComparator;
import com.dododo.ariadne.core.composer.FlowchartContractComposer;
import com.dododo.ariadne.core.composer.ParentFirstLargeTreeFlowchartContractComposer;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.util.StateManipulatorUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class RemoveStateDuplicatesJob extends AbstractJob {

    @Override
    public void run() {
        FlowchartContractComposer composer = selectComposerBasedOnFlowchartTreeSize(
                new ParentFirstLargeTreeFlowchartContractComposer(),
                new FlowchartContractComposer());

        removeChainStateDuplicates(composer);
        removeOptionDuplicates(composer);
    }

    private void removeChainStateDuplicates(FlowchartContractComposer factory) {
        StateCollector<ChainState> chainStateCollector = new GenericStateCollector<>(factory, ChainState.class);
        StateComparator comparator = new StateComparator(factory);

        Map<State, Set<State>> map = new HashMap<>();
        List<ChainState> states = new ArrayList<>(chainStateCollector.collect(getFlowchart()));

        while (!states.isEmpty()) {
            State s1 = states.remove(0);

            if (s1 instanceof Option) {
                continue;
            }

            int i = 0;

            while (i < states.size()) {
                State s2 = states.get(i);

                if (s1 != s2 && s1.compareTo(s2) == 0) {
                    map.putIfAbsent(s1, new HashSet<>());
                    map.get(s1).add(s2);

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

    private void removeOptionDuplicates(FlowchartContractComposer factory) {
        StateCollector<Menu> menuStateCollector = new GenericStateCollector<>(factory, Menu.class);
        StateComparator comparator = new StateComparator(factory);

        Map<Menu, Set<Option>> map = new HashMap<>();
        Set<Menu> menus = menuStateCollector.collect(getFlowchart());

        for (Menu menu : menus) {
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

        map.forEach((key, value) -> value.forEach(key::removeBranch));
    }
}
