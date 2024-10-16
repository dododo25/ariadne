package com.dododo.ariadne.core.job;

import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.CycleEntryState;
import com.dododo.ariadne.core.model.CycleMarker;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.SimpleState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.util.FlowchartManipulatorUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
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

    private static boolean isEqual(State s1, State s2) {
        AtomicBoolean res = new AtomicBoolean(true);

        FlowchartContract callback = new FlowchartContractAdapter() {

            @Override
            public void accept(EntryState state) {
                acceptChainState(state);
            }

            @Override
            public void accept(CycleMarker marker) {
                acceptSimpleState(marker);
            }

            @Override
            public void accept(CycleEntryState entryState) {
                acceptSimpleState(entryState);
            }

            @Override
            public void accept(Text text) {
                acceptSimpleState(text);
            }

            @Override
            public void accept(Option option) {
                acceptSimpleState(option);
            }

            @Override
            public void accept(Reply reply) {
                acceptChainState(reply);

                if (s2 instanceof Reply
                        && Objects.equals(reply.getCharacter(), ((Reply) s2).getCharacter())
                        && Objects.equals(reply.getLine(), ((Reply) s2).getLine())) {
                    return;
                }

                res.set(false);
            }

            @Override
            public void accept(ConditionalOption option) {
                acceptChainState(option);

                if (s2 instanceof ConditionalOption
                        && Objects.equals(option.getValue(), ((ConditionalOption) s2).getValue())
                        && Objects.equals(option.getCondition(), ((ConditionalOption) s2).getCondition())) {
                    return;
                }

                res.set(false);
            }

            private void acceptSimpleState(SimpleState simpleState) {
                acceptChainState(simpleState);

                if (s2 instanceof SimpleState
                        && Objects.equals(simpleState.getValue(), ((SimpleState) s2).getValue())) {
                    return;
                }

                res.set(false);
            }

            private void acceptChainState(ChainState chainState) {
                if (s2 instanceof ChainState && chainState.getNext() == ((ChainState) s2).getNext()) {
                    return;
                }

                res.set(false);
            }
        };

        s1.accept(callback);

        return res.get();
    }
}
