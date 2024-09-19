package com.dododo.ariadne.ct.set;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.test.annotation.EdgeRule;
import com.dododo.ariadne.test.annotation.NodeRule;
import com.dododo.ariadne.test.annotation.Ruleset;

@Ruleset
public class FlowchartRuleset {

    @NodeRule(type = EntryState.class)
    public State createEntryState() {
        return new EntryState();
    }

    @NodeRule(type = Text.class, params = "value")
    public State createText(String value) {
        return new Text(value);
    }

    @NodeRule(type = Reply.class, params = {"character", "line"})
    public State createReply(String character, String line) {
        return new Reply(character, line);
    }

    @NodeRule(type = Menu.class)
    public State createMenu() {
        return new Menu();
    }

    @NodeRule(type = Option.class, params = "value")
    public State createOption(String value) {
        return new Option(value);
    }

    @NodeRule(type = ConditionalOption.class, params = {"value", "condition"})
    public State createConditionalOption(String value, String condition) {
        return new ConditionalOption(value, condition);
    }

    @NodeRule(type = Switch.class, params = "condition")
    public State createSwitch(String condition) {
        return new Switch(condition);
    }

    @NodeRule(type = EndPoint.class)
    public State createEndPoint() {
        return new EndPoint();
    }

    @EdgeRule(type = {EntryState.class, Text.class, Reply.class, Option.class, ConditionalOption.class})
    public void joinChainState(State s1, State s2) {
        ((ChainState) s1).setNext(s2);
    }

    @EdgeRule(type = Menu.class)
    public void joinMenu(State s1, State s2) {
        ((Menu) s1).addBranch((Option) s2);
    }
    
    @EdgeRule(type = Switch.class, params = "switchBranch")
    public void joinSwitch(State s1, State s2, String switchBranch) {
        if (switchBranch == null) {
            throw new NullPointerException("Unknown value for switchBranch attribute");
        }

        if (switchBranch.equals("true")) {
            ((Switch) s1).setTrueBranch(s2);
        } else if (switchBranch.equals("false")) {
            ((Switch) s1).setFalseBranch(s2);
        } else {
            throw new IllegalArgumentException("Illegal value for switchBranch attribute");
        }
    }
}
