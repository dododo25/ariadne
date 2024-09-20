package com.dododo.ariadne.renpy.util;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.extended.model.ComplexMenu;
import com.dododo.ariadne.extended.model.ComplexOption;
import com.dododo.ariadne.extended.model.ComplexState;
import com.dododo.ariadne.extended.model.ComplexSwitch;
import com.dododo.ariadne.extended.model.ComplexSwitchBranch;
import com.dododo.ariadne.extended.model.GoToPoint;
import com.dododo.ariadne.extended.model.Marker;
import com.dododo.ariadne.extended.model.PassState;
import com.dododo.ariadne.renpy.model.VariableGroupComplexState;
import com.dododo.ariadne.renpy.model.LabelledGroupComplexState;
import com.dododo.ariadne.renpy.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.model.CallToState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public final class RenPyStateCopyUtil {

    private RenPyStateCopyUtil() {}

    public static State copy(State flowchart) {
        Map<State, State> map = new HashMap<>();

        copyStates(flowchart, map);
        copyEdges(flowchart, map);

        return map.get(flowchart);
    }

    private static void copyStates(State flowchart, Map<State, State> map) {
        RenPyFlowchartContract callback = new RenPyFlowchartContract() {

            @Override
            public void accept(EntryState state) {
                map.put(state, new EntryState());
            }

            @Override
            public void accept(Text text) {
                map.put(text, new Text(text.getValue()));
            }

            @Override
            public void accept(Reply reply) {
                map.put(reply, new Reply(reply.getCharacter(), reply.getLine()));
            }

            @Override
            public void accept(Menu menu) {
                map.put(menu, new Menu());
            }

            @Override
            public void accept(Option option) {
                map.put(option, new Option(option.getValue()));
            }

            @Override
            public void accept(ConditionalOption option) {
                map.put(option, new ConditionalOption(option.getValue(), option.getCondition()));
            }

            @Override
            public void accept(Switch aSwitch) {
                map.put(aSwitch, new Switch(aSwitch.getCondition()));
            }

            @Override
            public void accept(EndPoint point) {
                map.put(point, new EndPoint());
            }

            @Override
            public void accept(ComplexState state) {
                map.put(state, new ComplexState());
            }

            @Override
            public void accept(Marker marker) {
                map.put(marker, new Marker(marker.getValue()));
            }

            @Override
            public void accept(PassState state) {
                map.put(state, new PassState());
            }

            @Override
            public void accept(ComplexMenu complexMenu) {
                map.put(complexMenu, new ComplexMenu());
            }

            @Override
            public void accept(ComplexOption complexOption) {
                map.put(complexOption, new ComplexOption(complexOption.getValue(), complexOption.getCondition()));
            }

            @Override
            public void accept(CallToState callState) {
                map.put(callState, new CallToState(callState.getValue()));
            }

            @Override
            public void accept(LabelledGroupComplexState group) {
                map.put(group, new LabelledGroupComplexState(group.getValue()));
            }

            @Override
            public void accept(VariableGroupComplexState group) {
                map.put(group, new VariableGroupComplexState());
            }

            @Override
            public void accept(GoToPoint point) {
                map.put(point, new GoToPoint(point.getValue()));
            }

            @Override
            public void accept(ComplexSwitch complexSwitch) {
                map.put(complexSwitch, new ComplexState());
            }

            @Override
            public void accept(ComplexSwitchBranch branch) {
                map.put(branch, new ComplexSwitchBranch(branch.getValue(), branch.isFalseBranch()));
            }
        };
        FlowchartMouse mouse = new RenPyFlowchartMouse();

        mouse.accept(flowchart, callback);
    }

    private static void copyEdges(State flowchart, Map<State, State> map) {
        RenPyFlowchartContract callback = new RenPyFlowchartContractAdapter() {

            @Override
            public void accept(EntryState state) {
                acceptChainState(state);
            }

            @Override
            public void accept(Text text) {
                acceptChainState(text);
            }

            @Override
            public void accept(Reply reply) {
                acceptChainState(reply);
            }

            @Override
            public void accept(Option option) {
                acceptChainState(option);
            }

            @Override
            public void accept(ConditionalOption option) {
                acceptChainState(option);
            }

            @Override
            public void accept(PassState state) {
                acceptChainState(state);
            }

            @Override
            public void accept(ComplexMenu complexMenu) {
                acceptComplexState(complexMenu);
            }

            @Override
            public void accept(ComplexOption complexOption) {
                acceptComplexState(complexOption);
            }

            @Override
            public void accept(CallToState callState) {
                acceptChainState(callState);
            }

            @Override
            public void accept(LabelledGroupComplexState group) {
                acceptComplexState(group);
            }

            @Override
            public void accept(VariableGroupComplexState group) {
                acceptComplexState(group);
            }

            @Override
            public void accept(ComplexState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(Marker marker) {
                acceptChainState(marker);
            }

            @Override
            public void accept(ComplexSwitch complexSwitch) {
                acceptComplexState(complexSwitch);
            }

            @Override
            public void accept(ComplexSwitchBranch branch) {
                acceptComplexState(branch);
            }

            @Override
            public void accept(Menu menu) {
                Menu menuCopy = (Menu) map.get(menu);

                menu.branchesStream().map(map::get)
                        .forEach(option -> menuCopy.addBranch((Option) option));
            }

            @Override
            public void accept(Switch aSwitch) {
                acceptState(aSwitch, aSwitch.getTrueBranch(), (root, child) -> ((Switch) root).setTrueBranch(child));
                acceptState(aSwitch, aSwitch.getFalseBranch(), (root, child) -> ((Switch) root).setFalseBranch(child));
            }

            private void acceptChainState(ChainState state) {
                acceptState(state, state.getNext(), (root, child) -> ((ChainState) root).setNext(child));
            }

            private void acceptComplexState(ComplexState state) {
                ComplexState copy = (ComplexState) map.get(state);
                state.childrenStream().map(map::get).forEach(copy::addChild);
            }

            private void acceptState(State root, State child, BiConsumer<State, State> consumer) {
                State stateCopy = map.get(root);

                if (child != null) {
                    consumer.accept(stateCopy, map.get(child));
                }
            }
        };
        FlowchartMouse mouse = new RenPyFlowchartMouse();

        mouse.accept(flowchart, callback);
    }
}
