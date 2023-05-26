package com.dododo.ariadne.xml.jaxb.test.supplier;

import com.dododo.ariadne.test.annotation.RuleSetSupplier;
import com.dododo.ariadne.test.rule.NodeRule;
import com.dododo.ariadne.test.rule.RuleSet;
import com.dododo.ariadne.xml.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.xml.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.xml.jaxb.model.JaxbEndState;
import com.dododo.ariadne.xml.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.xml.jaxb.model.JaxbMarker;
import com.dododo.ariadne.xml.jaxb.model.JaxbPassState;
import com.dododo.ariadne.xml.jaxb.model.JaxbRootState;
import com.dododo.ariadne.xml.jaxb.model.JaxbState;
import com.dododo.ariadne.xml.jaxb.model.JaxbStatement;
import com.dododo.ariadne.xml.jaxb.model.JaxbSwitchBranch;

public class JaxbFlowchartRulesSupplier {

    @RuleSetSupplier
    public RuleSet createRuleForJaxbRootState() {
        return createRuleForComplexState(JaxbRootState.class, (id, attrs) -> new JaxbRootState());
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbComplexSwitch() {
        return createRuleForComplexState(JaxbComplexSwitch.class, (id, attrs) -> new JaxbComplexSwitch());
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbSwitchBranch() {
        return createRuleForComplexState(JaxbSwitchBranch.class,
                (id, attrs) -> new JaxbSwitchBranch(attrs.getValue("condition")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbStatement() {
        return createRuleForState(JaxbStatement.class, (id, attrs) -> new JaxbStatement(attrs.getValue("value")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbMarker() {
        return createRuleForState(JaxbMarker.class, (id, attrs) -> new JaxbMarker(attrs.getValue("value")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbGoToState() {
        return createRuleForState(JaxbGoToState.class,
                (id, attrs) -> new JaxbGoToState(attrs.getValue("value")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbPassState() {
        return createRuleForState(JaxbPassState.class, (id, attrs) -> new JaxbPassState());
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbEndState() {
        return createRuleForState(JaxbEndState.class, (id, attrs) -> new JaxbEndState());
    }

    private RuleSet createRuleForComplexState(Class<? extends JaxbComplexState> type, NodeRule rule) {
        return prepareRuleForState(type, rule)
                .setEdgeRule((o1, o2, attrs) -> ((JaxbComplexState) o1).addChild((JaxbState) o2))
                .build();
    }

    private RuleSet createRuleForState(Class<? extends JaxbState> type, NodeRule rule) {
        return prepareRuleForState(type, rule).build();
    }

    private RuleSet.Builder prepareRuleForState(Class<? extends JaxbState> type, NodeRule rule) {
        return new RuleSet.Builder(type)
                .setNodeRule(rule);
    }
}
