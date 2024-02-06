package com.dododo.ariadne.core.util;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.strategy.large.LargeTreeFlowchartMouseStrategy;
import com.dododo.ariadne.core.mouse.strategy.large.ParentFirstLargeTreeFlowchartMouseStrategy;

import java.util.Collection;
import java.util.HashSet;

import static com.dododo.ariadne.core.mouse.strategy.large.LargeTreeFlowchartMouseStrategy.DEFAULT_DEPTH;

public class FlowchartTreeUtil {

    private FlowchartTreeUtil() {}

    public static boolean isLarge(State root) {
        Collection<State> states = new HashSet<>();

        LargeTreeFlowchartMouseStrategy strategy =
                new ParentFirstLargeTreeFlowchartMouseStrategy(states, DEFAULT_DEPTH);
        FlowchartMouse mouse = new FlowchartMouse(strategy);

        root.accept(mouse);

        return !states.isEmpty();
    }
}
