package com.dododo.ariadne.jt.supplier;

import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbEndState;
import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.jaxb.model.JaxbLabel;
import com.dododo.ariadne.jaxb.model.JaxbMenu;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbReply;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbSimpleState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.model.JaxbText;
import com.dododo.ariadne.test.annotation.RuleSetSupplier;
import com.dododo.ariadne.test.rule.NodeRule;
import com.dododo.ariadne.test.rule.RuleSet;

import java.util.function.Function;

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
    public RuleSet createRuleForJaxbMenu() {
        return createRuleForComplexState(JaxbMenu.class, (id, attrs) -> new JaxbMenu());
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbOption() {
        return createRuleForComplexState(JaxbOption.class, (id, attrs) ->
                new JaxbOption(attrs.getValue("value"), attrs.getValue("condition")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbSwitchBranch() {
        return createRuleForComplexState(JaxbSwitchBranch.class,
                (id, attrs) -> new JaxbSwitchBranch(attrs.getValue("condition")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbText() {
        return createRuleForSimpleState(JaxbText.class, JaxbText::new);
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbReply() {
        return createRuleForState(JaxbReply.class, (id, attrs) ->
                new JaxbReply(attrs.getValue("character"), attrs.getValue("line")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbLabel() {
        return createRuleForSimpleState(JaxbLabel.class, JaxbLabel::new);
    }

    @RuleSetSupplier
    public RuleSet createRuleForJaxbGoToState() {
        return createRuleForSimpleState(JaxbGoToState.class, JaxbGoToState::new);
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

    private <T extends JaxbSimpleState> RuleSet createRuleForSimpleState(Class<T> type, Function<String, T> function) {
        return createRuleForState(type, (id, attrs) -> function.apply(attrs.getValue("value")));
    }

    private RuleSet createRuleForState(Class<? extends JaxbState> type, NodeRule rule) {
        return prepareRuleForState(type, rule).build();
    }

    private RuleSet.Builder prepareRuleForState(Class<? extends JaxbState> type, NodeRule rule) {
        return new RuleSet.Builder(type)
                .setNodeRule(rule);
    }
}
