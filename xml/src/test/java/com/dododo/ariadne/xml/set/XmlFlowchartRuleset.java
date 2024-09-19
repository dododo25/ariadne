package com.dododo.ariadne.xml.set;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.test.annotation.EdgeRule;
import com.dododo.ariadne.test.annotation.NodeRule;
import com.dododo.ariadne.test.annotation.Ruleset;
import com.dododo.ariadne.xml.model.ComplexMenu;
import com.dododo.ariadne.xml.model.ComplexOption;
import com.dododo.ariadne.xml.model.ComplexState;
import com.dododo.ariadne.xml.model.ComplexSwitch;
import com.dododo.ariadne.xml.model.ComplexSwitchBranch;
import com.dododo.ariadne.xml.model.GoToPoint;
import com.dododo.ariadne.xml.model.Marker;
import com.dododo.ariadne.xml.model.PassState;

@Ruleset
public class XmlFlowchartRuleset {

    @NodeRule(type = ComplexState.class)
    public State createComplexState() {
        return new ComplexState();
    }

    @NodeRule(type = ComplexSwitch.class)
    public State createComplexSwitch() {
        return new ComplexSwitch();
    }

    @NodeRule(type = ComplexSwitchBranch.class, params = "condition")
    public State createComplexSwitchBranch(String condition) {
        return new ComplexSwitchBranch(condition);
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
