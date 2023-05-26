package com.dododo.ariadne.xml.common.test.supplier;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.test.annotation.RuleSetSupplier;
import com.dododo.ariadne.test.rule.NodeRule;
import com.dododo.ariadne.test.rule.RuleSet;
import com.dododo.ariadne.xml.common.model.ComplexState;
import com.dododo.ariadne.xml.common.model.ComplexSwitch;
import com.dododo.ariadne.xml.common.model.GoToPoint;
import com.dododo.ariadne.xml.common.model.Marker;
import com.dododo.ariadne.xml.common.model.PassState;
import com.dododo.ariadne.xml.common.model.SwitchBranch;

public class XmlFlowchartRulesSupplier {

    @RuleSetSupplier
    public RuleSet createRuleForComplexState() {
        return new RuleSet.Builder(ComplexState.class)
                .setNodeRule((id, attrs) -> new ComplexState())
                .setEdgeRule((o1, o2, attrs) -> ((ComplexState) o1).addChild((State) o2))
                .build();
    }

    @RuleSetSupplier
    public RuleSet createRuleForComplexSwitch() {
        return new RuleSet.Builder(ComplexSwitch.class)
                .setNodeRule((id, attrs) -> new ComplexSwitch())
                .setEdgeRule((o1, o2, attrs) -> ((ComplexSwitch) o1).addChild((State) o2))
                .build();
    }

    @RuleSetSupplier
    public RuleSet createRuleForMarker() {
        return createRuleForChainState(Marker.class, (id, attrs) -> new Marker(attrs.getValue("value")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForPassState() {
        return createRuleForChainState(PassState.class, (id, attrs) -> new PassState());
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
