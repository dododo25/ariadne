package com.dododo.ariadne.renpy.common.util;

import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.mouse.strategy.large.ParentFirstRenPyLargeTreeFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.common.mouse.strategy.large.RenPyLargeTreeFlowchartMouseStrategy;

import java.util.Collection;
import java.util.HashSet;

import static com.dododo.ariadne.core.mouse.strategy.large.LargeTreeFlowchartMouseStrategy.DEFAULT_DEPTH;

public class RenPyFlowchartTreeUtil {

    private RenPyFlowchartTreeUtil() {}

    public static boolean isLarge(State root) {
        Collection<State> states = new HashSet<>();

        RenPyLargeTreeFlowchartMouseStrategy strategy =
                new ParentFirstRenPyLargeTreeFlowchartMouseStrategy(states, DEFAULT_DEPTH);
        RenPyFlowchartMouse mouse = new RenPyFlowchartMouse(strategy);

        root.accept(mouse);

        return !states.isEmpty();
    }
}
