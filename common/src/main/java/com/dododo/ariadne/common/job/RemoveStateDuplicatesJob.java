package com.dododo.ariadne.common.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
import com.dododo.ariadne.core.mouse.ParentFirstFlowchartMouse;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.strategy.FlowchartMouseStrategy;
import com.dododo.ariadne.core.mouse.strategy.ParentFirstFlowchartMouseStrategy;
import com.dododo.ariadne.core.util.StateManipulatorUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class RemoveStateDuplicatesJob extends AbstractJob {

    private ParentFirstFlowchartMouse mouse;

    @Override
    public void run() {
        this.mouse = new ParentFirstFlowchartMouse();

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

                        if (isEqual(option, nextOption)) {
                            map.computeIfAbsent(menu, m -> new HashSet<>()).add(nextOption);
                        }
                    }
                }
            }
        };

        mouse.accept(getFlowchart(), callback);
        map.forEach((key, value) -> value.forEach(key::removeBranch));
    }

    private boolean isEqual(State s1, State s2) {
        FlowchartMouseStrategy strategy = new ParentFirstFlowchartMouseStrategy();

        FlowchartContract c0 = new SimpleFlowchartContract() {
            @Override
            public void acceptState(State state) {
                // check is states are equal
            }
        };

        if (s1 == s2) {
            return true;
        }

        List<State> leftGrayStates = new ArrayList<>(Collections.singletonList(s1));
        List<State> rightGrayStates = new ArrayList<>(Collections.singletonList(s2));
        List<State> blackStates = new ArrayList<>();

        while (!leftGrayStates.isEmpty()) {
            if (leftGrayStates.size() != rightGrayStates.size()) {
                return false;
            }

            boolean allEqual = new HashSet<>(rightGrayStates).containsAll(leftGrayStates);

            if (allEqual) {
                return true;
            }

            s1 = leftGrayStates.remove(0);
            s2 = rightGrayStates.remove(0);

            if (s1.compareTo(s2) != 0) {
                return false;
            }

            s1.accept(strategy, c0, leftGrayStates, blackStates);
            s2.accept(strategy, c0, rightGrayStates, blackStates);
        }

        return rightGrayStates.isEmpty();
    }
}
