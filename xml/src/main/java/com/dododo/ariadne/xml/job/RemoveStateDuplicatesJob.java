package com.dododo.ariadne.xml.job;

import com.dododo.ariadne.common.job.AbstractJob;
import com.dododo.ariadne.core.collector.GenericStateCollector;
import com.dododo.ariadne.core.collector.StateCollector;
import com.dododo.ariadne.core.comparator.StateComparator;
import com.dododo.ariadne.core.factory.FlowchartMouseFactory;
import com.dododo.ariadne.core.model.ChainState;
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
        StateCollector<ChainState> chainStateCollector
                = new GenericStateCollector<>(new FlowchartMouseFactory(), ChainState.class);
        StateComparator comparator = new StateComparator(new FlowchartMouseFactory());

        Map<State, Set<State>> map = new HashMap<>();
        List<ChainState> states = new ArrayList<>(chainStateCollector.collect(getFlowchart()));

        while (!states.isEmpty()) {
            State s1 = states.remove(0);

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
}
