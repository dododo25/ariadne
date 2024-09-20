package com.dododo.ariadne.renpy.set;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.renpy.model.CallToState;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;
import com.dododo.ariadne.renpy.model.SkipComplexState;
import com.dododo.ariadne.renpy.model.VariableGroupComplexState;
import com.dododo.ariadne.test.annotation.EdgeRule;
import com.dododo.ariadne.test.annotation.NodeRule;
import com.dododo.ariadne.test.annotation.Ruleset;

@Ruleset
public class RenPyFlowchartRuleset {

    @NodeRule(type = CallToState.class, params = "value")
    public State createCallToState(String value) {
        return new CallToState(value);
    }

    @NodeRule(type = SkipComplexState.class)
    public State createSkipComplexState() {
        return new SkipComplexState();
    }

    @NodeRule(type = LabelledGroupComplexState.class, params = "value")
    public State createLabelledGroupComplexState(String value) {
        return new LabelledGroupComplexState(value);
    }

    @NodeRule(type = VariableGroupComplexState.class)
    public State createVariablesGroupComplexState() {
        return new VariableGroupComplexState();
    }

    @EdgeRule(type = CallToState.class)
    public void joinChainState(State s1, State s2) {
        ((ChainState) s1).setNext(s2);
    }

    @EdgeRule(type = {LabelledGroupComplexState.class, VariableGroupComplexState.class, SkipComplexState.class})
    public void joinComplexState(State s1, State s2) {
        ((ComplexState) s1).addChild(s2);
    }
}
