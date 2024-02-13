package com.dododo.ariadne.renpy.common.test.supplier;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.test.annotation.RuleSetSupplier;
import com.dododo.ariadne.test.rule.RuleSet;

public class RenPyFlowchartRulesSupplier {

    @RuleSetSupplier
    public RuleSet createRuleForCallToState() {
        return new RuleSet.Builder(CallToState.class)
                .setNodeRule( (id, attrs) -> new CallToState(attrs.getValue("value")))
                .setEdgeRule((o1, o2, attrs) -> ((ChainState) o1).setNext((State) o2))
                .build();
    }
}
