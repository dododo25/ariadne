package com.dododo.ariadne.extended.set;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.ComplexMenu;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.test.annotation.EdgeRule;
import com.dododo.ariadne.test.annotation.NodeRule;
import com.dododo.ariadne.test.annotation.Ruleset;

@Ruleset
public class ExtendedFlowchartRuleset {

    @NodeRule(type = ComplexState.class)
    public State createComplexState() {
        return new ComplexState();
    }

    @NodeRule(type = ComplexSwitch.class)
    public State createComplexSwitch() {
        return new ComplexSwitch();
    }

    @NodeRule(type = ComplexSwitchBranch.class, params = {"condition", "branchType"})
    public State createComplexSwitchBranch(String condition, String branchType) {
        if (branchType == null || "if".equalsIgnoreCase(branchType)) {
            return new ComplexSwitchBranch(condition, false);
        } else if ("else-if".equalsIgnoreCase(branchType)) {
            return new ComplexSwitchBranch(condition, true);
        } else if ("else".equalsIgnoreCase(branchType)) {
            return new ComplexSwitchBranch(true);
        } else {
            throw new IllegalArgumentException(String.format("Illegal branchType attribute value '%s'", branchType));
        }
    }

    @NodeRule(type = Marker.class, params = "value")
    public State createMarker(String value) {
        return new Marker(value);
    }

    @NodeRule(type = PassState.class)
    public State createPassState() {
        return new PassState();
    }

    @NodeRule(type = ComplexMenu.class)
    public State createComplexMenu() {
        return new ComplexMenu();
    }

    @NodeRule(type = ComplexOption.class, params = {"value", "condition"})
    public State createComplexOption(String value, String condition) {
        return new ComplexOption(value, condition);
    }

    @NodeRule(type = GoToPoint.class, params = "value")
    public State createRuleGoToPoint(String value) {
        return new GoToPoint(value);
    }

    @EdgeRule(type = {Marker.class, PassState.class})
    public void joinChainState(State s1, State s2) {
        ((ChainState) s1).setNext(s2);
    }

    @EdgeRule(type = {
            ComplexState.class,
            ComplexMenu.class,
            ComplexOption.class,
            ComplexSwitch.class,
            ComplexSwitchBranch.class
    })
    public void joinComplexState(State s1, State s2) {
        ((ComplexState) s1).addChild(s2);
    }
}
