package com.dododo.ariadne.ct.supplier;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.test.annotation.RuleSetSupplier;
import com.dododo.ariadne.test.rule.NodeRule;
import com.dododo.ariadne.test.rule.RuleSet;

public class FlowchartRulesSupplier {

    @RuleSetSupplier
    public RuleSet createRuleForEntryState() {
        return createRuleForChainState(EntryState.class, (id, attrs) -> new EntryState());
    }

    @RuleSetSupplier
    public RuleSet createRuleForStatement() {
        return createRuleForChainState(Statement.class,
                (id, attrs) -> new Statement(attrs.getValue("value")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForSwitch() {
        return new RuleSet.Builder(Switch.class)
                .setNodeRule((id, attrs) -> new Switch(attrs.getValue("condition")))
                .setEdgeRule((o1, o2, attrs) -> {
                    Switch aSwitch = (Switch) o1;
                    State next = (State) o2;

                    String switchBranch = attrs.getValue("switchBranch");

                    if (switchBranch == null) {
                        throw new NullPointerException("Unknown value for switchBranch attribute");
                    }

                    if (switchBranch.equals("true")) {
                        aSwitch.setTrueBranch(next);
                    } else if (switchBranch.equals("false")) {
                        aSwitch.setFalseBranch(next);
                    }
                })
                .build();
    }

    @RuleSetSupplier
    public RuleSet createRuleForEndPoint() {
        return new RuleSet.Builder(EndPoint.class)
                .setNodeRule((id, attrs) -> new EndPoint())
                .build();
    }

    private RuleSet createRuleForChainState(Class<? extends ChainState> type, NodeRule rule) {
        return new RuleSet.Builder(type)
                .setNodeRule(rule)
                .setEdgeRule((o1, o2, attrs) -> ((ChainState) o1).setNext((State) o2))
                .build();
    }
}
