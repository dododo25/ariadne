package com.dododo.ariadne.renpy.common.test.supplier;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.model.ComplexSwitch;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.model.PassState;
import com.dododo.ariadne.renpy.common.model.SwitchBranch;
import com.dododo.ariadne.test.annotation.RuleSetSupplier;
import com.dododo.ariadne.test.rule.NodeRule;
import com.dododo.ariadne.test.rule.RuleSet;

public class RenPyFlowchartRulesSupplier {

    @RuleSetSupplier
    public RuleSet createRuleForComplexState() {
        return createRuleForComplexState(ComplexState.class, (id, attrs) -> new ComplexState());
    }

    @RuleSetSupplier
    public RuleSet createRuleForComplexSwitch() {
        return createRuleForComplexState(ComplexSwitch.class, (id, attrs) -> new ComplexSwitch());
    }

    @RuleSetSupplier
    public RuleSet createRuleForPassState() {
        return createRuleForChainState(PassState.class,
                (id, attrs) -> new PassState());
    }

    @RuleSetSupplier
    public RuleSet createRuleForLabelledGroup() {
        return createRuleForChainState(LabelledGroup.class,
                (id, attrs) -> new LabelledGroup(attrs.getValue("value")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForCallToState() {
        return createRuleForChainState(CallToState.class,
                (id, attrs) -> new CallToState(attrs.getValue("value")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJumpToPoint() {
        return new RuleSet.Builder(JumpToPoint.class)
                .setNodeRule((id, attrs) -> new JumpToPoint(attrs.getValue("value")))
                .build();
    }

    @RuleSetSupplier
    public RuleSet createRuleForSwitchBranch() {
        return createRuleForChainState(SwitchBranch.class,
                (id, attrs) -> new SwitchBranch(attrs.getValue("condition")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForReply() {
        return createRuleForChainState(Reply.class,
                (id, attrs) -> new Reply(attrs.getValue("character"), attrs.getValue("line")));
    }

    private RuleSet createRuleForChainState(Class<? extends ChainState> type, NodeRule rule) {
        return new RuleSet.Builder(type)
                .setNodeRule(rule)
                .setEdgeRule((o1, o2, attrs) -> ((ChainState) o1).setNext((State) o2))
                .build();
    }

    private RuleSet createRuleForComplexState(Class<? extends ComplexState> type, NodeRule rule) {
        return new RuleSet.Builder(type)
                .setNodeRule(rule)
                .setEdgeRule((o1, o2, attrs) -> ((ComplexState) o1).addChild((State) o2))
                .build();
    }
}
