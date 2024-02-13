package com.dododo.ariadne.renpy.jaxb.test.supplier;

import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbCallToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbRenPyMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSkipComplexState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import com.dododo.ariadne.test.annotation.RuleSetSupplier;
import com.dododo.ariadne.test.rule.NodeRule;
import com.dododo.ariadne.test.rule.RuleSet;

public class JaxbFlowchartRulesSupplier {

    @RuleSetSupplier
    public RuleSet createRuleForJaxbInitGroupState() {
        return createRuleForComplexState(JaxbInitGroupState.class, (id, attrs) -> new JaxbInitGroupState());
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbSkipComplexState() {
        return createRuleForComplexState(JaxbSkipComplexState.class, (id, attrs) -> new JaxbSkipComplexState());
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbRenPyMenu() {
        return createRuleForComplexState(JaxbRenPyMenu.class, (id, attrs) -> new JaxbRenPyMenu());
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbLabelledGroup() {
        return createRuleForComplexState(JaxbLabelledGroup.class,
                (id, attrs) -> new JaxbLabelledGroup(attrs.getValue("value")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbSwitchFalseBranch() {
        return createRuleForComplexState(JaxbSwitchFalseBranch.class,
                (id, attrs) -> new JaxbSwitchFalseBranch(attrs.getValue("condition")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbCallToState() {
        return prepareRuleForState(JaxbCallToState.class,
                (id, attrs) -> new JaxbCallToState(attrs.getValue("value"))).build();
    }

    private RuleSet createRuleForComplexState(Class<? extends JaxbComplexState> type, NodeRule rule) {
        return prepareRuleForState(type, rule)
                .setEdgeRule((o1, o2, attrs) -> ((JaxbComplexState) o1).addChild((JaxbState) o2))
                .build();
    }

    private RuleSet.Builder prepareRuleForState(Class<? extends JaxbState> type, NodeRule rule) {
        return new RuleSet.Builder(type)
                .setNodeRule(rule);
    }
}
