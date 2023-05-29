package com.dododo.ariadne.renpy.jaxb.test.supplier;

import com.dododo.ariadne.renpy.jaxb.model.JaxbCallToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbEndState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbJumpToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbOption;
import com.dododo.ariadne.renpy.jaxb.model.JaxbPassState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbReply;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSkipComplexState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbStatement;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;
import com.dododo.ariadne.test.annotation.RuleSetSupplier;
import com.dododo.ariadne.test.rule.NodeRule;
import com.dododo.ariadne.test.rule.RuleSet;

public class JaxbFlowchartRulesSupplier {

    @RuleSetSupplier
    public RuleSet createRuleForJaxbGroupState() {
        return createRuleForComplexState(JaxbGroupState.class, (id, attrs) -> new JaxbGroupState());
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbSkipComplexState() {
        return createRuleForComplexState(JaxbSkipComplexState.class, (id, attrs) -> new JaxbSkipComplexState());
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbMenu() {
        return createRuleForComplexState(JaxbMenu.class, (id, attrs) -> new JaxbMenu());
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbOption() {
        return createRuleForComplexState(JaxbOption.class,
                (id, attrs) -> new JaxbOption(attrs.getValue("value"), attrs.getValue("condition")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbComplexSwitch() {
        return createRuleForComplexState(JaxbComplexSwitch.class, (id, attrs) -> new JaxbComplexSwitch());
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbLabelledGroup() {
        return createRuleForComplexState(JaxbLabelledGroup.class,
                (id, attrs) -> new JaxbLabelledGroup(attrs.getValue("value")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbSwitchBranch() {
        return createRuleForComplexState(JaxbSwitchBranch.class,
                (id, attrs) -> new JaxbSwitchBranch(attrs.getValue("condition")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbSwitchFalseBranch() {
        return createRuleForComplexState(JaxbSwitchFalseBranch.class,
                (id, attrs) -> new JaxbSwitchFalseBranch(attrs.getValue("condition")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbStatement() {
        return createRuleForState(JaxbStatement.class, (id, attrs) -> new JaxbStatement(attrs.getValue("value")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbReply() {
        return createRuleForState(JaxbReply.class,
                (id, attrs) -> new JaxbReply(attrs.getValue("character"), attrs.getValue("line")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbJumpToState() {
        return createRuleForState(JaxbJumpToState.class,
                (id, attrs) -> new JaxbJumpToState(attrs.getValue("value")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbCallToState() {
        return createRuleForState(JaxbCallToState.class,
                (id, attrs) -> new JaxbCallToState(attrs.getValue("value")));
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
