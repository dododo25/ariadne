package com.dododo.ariadne.renpy.common.util;

import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.ConditionalOption;
import com.dododo.ariadne.core.model.CycleEntryState;
import com.dododo.ariadne.core.model.CycleMarker;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.Menu;
import com.dododo.ariadne.core.model.Option;
import com.dododo.ariadne.core.model.Reply;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContract;
import com.dododo.ariadne.renpy.common.contract.RenPyFlowchartContractAdapter;
import com.dododo.ariadne.renpy.common.model.CallToState;
import com.dododo.ariadne.renpy.common.model.ComplexState;
import com.dododo.ariadne.renpy.common.model.ComplexSwitch;
import com.dododo.ariadne.renpy.common.model.JumpToPoint;
import com.dododo.ariadne.renpy.common.model.LabelledGroup;
import com.dododo.ariadne.renpy.common.model.PassState;
import com.dododo.ariadne.renpy.common.model.SwitchBranch;
import com.dododo.ariadne.renpy.common.mouse.RenPyFlowchartMouse;
import com.dododo.ariadne.renpy.common.mouse.strategy.ParentFirstRenPyFlowchartMouseStrategy;

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
            public void accept(CycleMarker marker) {
                map.put(marker, new CycleMarker(marker.getValue()));
            }

            @Override
            public void accept(CycleEntryState state) {
                map.put(state, new CycleEntryState(state.getValue()));
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
            public void accept(PassState state) {
                map.put(state, new PassState());
            }

            @Override
            public void accept(LabelledGroup group) {
                map.put(group, new LabelledGroup(group.getValue()));
            }

            @Override
            public void accept(CallToState callState) {
                map.put(callState, new CallToState(callState.getValue()));
            }

            @Override
            public void accept(JumpToPoint point) {
                map.put(point, new JumpToPoint(point.getValue()));
            }

            @Override
            public void accept(ComplexSwitch complexSwitch) {
                map.put(complexSwitch, new ComplexState());
            }

            @Override
            public void accept(SwitchBranch branch) {
                map.put(branch, new SwitchBranch(branch.getValue()));
            }
        };
        FlowchartMouse mouse = new RenPyFlowchartMouse(callback, new ParentFirstRenPyFlowchartMouseStrategy());

        flowchart.accept(mouse);
    }

    private static void copyEdges(State flowchart, Map<State, State> map) {
        RenPyFlowchartContract callback = new RenPyFlowchartContractAdapter() {

            @Override
            public void accept(EntryState state) {
                acceptChainState(state);
            }

            @Override
            public void accept(CycleMarker marker) {
                acceptChainState(marker);
            }

            @Override
            public void accept(CycleEntryState state) {
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
            public void accept(LabelledGroup group) {
                acceptChainState(group);
            }

            @Override
            public void accept(CallToState callState) {
                acceptChainState(callState);
            }

            @Override
            public void accept(SwitchBranch branch) {
                acceptChainState(branch);
            }

            @Override
            public void accept(ComplexState state) {
                acceptComplexState(state);
            }

            @Override
            public void accept(ComplexSwitch complexSwitch) {
                acceptComplexState(complexSwitch);
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
        FlowchartMouse mouse = new RenPyFlowchartMouse(callback, new ParentFirstRenPyFlowchartMouseStrategy());

        flowchart.accept(mouse);
    }
}
