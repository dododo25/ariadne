package com.dododo.ariadne.ct.supplier;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.CycleEntryState;
import com.dododo.ariadne.core.model.CycleMarker;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.SimpleState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.test.annotation.RuleSetSupplier;
import com.dododo.ariadne.test.rule.NodeRule;
import com.dododo.ariadne.test.rule.RuleSet;

import java.util.function.Function;

public class FlowchartRulesSupplier {

    @RuleSetSupplier
    public RuleSet createRuleForEntryState() {
        return createRuleForChainState(EntryState.class, (id, attrs) -> new EntryState());
    }

    @RuleSetSupplier
    public RuleSet createRuleForCycleMarker() {
        return createRuleForSimpleState(CycleMarker.class, CycleMarker::new);
    }

    @RuleSetSupplier
    public RuleSet createRuleForCycleEntryState() {
        return createRuleForSimpleState(CycleEntryState.class, CycleEntryState::new);
    }

    @RuleSetSupplier
    public RuleSet createRuleForText() {
        return createRuleForSimpleState(Text.class, Text::new);
    }

    @RuleSetSupplier
    public RuleSet createRuleForReply() {
        return createRuleForChainState(Reply.class,
                (id, attrs) -> new Reply(attrs.getValue("character"), attrs.getValue("line")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForMenu() {
        return new RuleSet.Builder(Menu.class)
                .setNodeRule((id, attrs) -> new Menu())
                .setEdgeRule((o1, o2, attrs) -> ((Menu) o1).addBranch((Option) o2))
                .build();
    }

    @RuleSetSupplier
    public RuleSet createRuleForOption() {
        return createRuleForSimpleState(Option.class, Option::new);
    }

    @RuleSetSupplier
    public RuleSet createRuleForConditionalOption() {
        return createRuleForChainState(ConditionalOption.class,
                (id, attrs) -> new ConditionalOption(attrs.getValue("value"), attrs.getValue("condition")));
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

    private <T extends SimpleState> RuleSet createRuleForSimpleState(Class<T> type, Function<String, T> function) {
        return createRuleForChainState(type, (id, attrs) -> function.apply(attrs.getValue("value")));
    }

    private RuleSet createRuleForChainState(Class<? extends ChainState> type, NodeRule rule) {
        return new RuleSet.Builder(type)
                .setNodeRule(rule)
                .setEdgeRule((o1, o2, attrs) -> ((ChainState) o1).setNext((State) o2))
                .build();
    }
}
