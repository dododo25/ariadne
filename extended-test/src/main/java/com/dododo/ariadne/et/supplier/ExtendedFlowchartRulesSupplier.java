package com.dododo.ariadne.et.supplier;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Label;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.extended.model.SwitchBranch;
import com.dododo.ariadne.test.annotation.RuleSetSupplier;
import com.dododo.ariadne.test.rule.NodeRule;
import com.dododo.ariadne.test.rule.RuleSet;

public class ExtendedFlowchartRulesSupplier {

    @RuleSetSupplier
    public RuleSet createRuleForComplexSwitch() {
        return new RuleSet.Builder(ComplexSwitch.class)
                .setNodeRule((id, attrs) -> new ComplexSwitch())
                .setEdgeRule((o1, o2, attrs) -> ((ComplexSwitch) o1).addChild((State) o2))
                .build();
    }

    @RuleSetSupplier
    public RuleSet createRuleForLabel() {
        return createRuleForChainState(Label.class, (id, attrs) -> new Label(attrs.getValue("value")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForPassState() {
        return createRuleForChainState(PassState.class, (id, attrs) -> new PassState());
    }

    @RuleSetSupplier
    public RuleSet createRuleForComplexState() {
        return new RuleSet.Builder(ComplexState.class)
                .setNodeRule((id, attrs) -> new ComplexState())
                .setEdgeRule((o1, o2, attrs) -> ((ComplexState) o1).addChild((State) o2))
                .build();
    }

    @RuleSetSupplier
    public RuleSet createRuleForSwitchBranch() {
        return createRuleForChainState(SwitchBranch.class,
                (id, attrs) -> new SwitchBranch(attrs.getValue("condition")));
    }

    @RuleSetSupplier
    public RuleSet createRuleGoToPoint() {
        return new RuleSet.Builder(GoToPoint.class)
                .setNodeRule((id, attrs) -> new GoToPoint(attrs.getValue("value")))
                .build();
    }

    private RuleSet createRuleForChainState(Class<? extends ChainState> type, NodeRule rule) {
        return new RuleSet.Builder(type)
                .setNodeRule(rule)
                .setEdgeRule((o1, o2, attrs) -> ((ChainState) o1).setNext((State) o2))
                .build();
    }
}
