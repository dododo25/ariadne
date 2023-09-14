package com.dododo.ariadne.thread.job;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.contract.FlowchartContractAdapter;
import com.dododo.ariadne.core.contract.SimpleFlowchartContract;
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
import com.dododo.ariadne.core.model.Switch;
import com.dododo.ariadne.core.model.Text;
import com.dododo.ariadne.core.mouse.FlowchartMouse;
import com.dododo.ariadne.core.mouse.strategy.ParentFirstFlowchartMouseStrategy;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbEndState;
import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.jaxb.model.JaxbMarker;
import com.dododo.ariadne.jaxb.model.JaxbMenu;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbReply;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.model.JaxbText;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class PrepareJaxbStateJob extends ThreadAbstractJob {

    public PrepareJaxbStateJob(AtomicReference<JaxbState> jaxbStateRef) {
        super(null, jaxbStateRef, null);
    }

    @Override
    public void run() {
        Map<State, JaxbState> stateMap = new HashMap<>();
        Map<State, Collection<State>> statesToSplit = new TreeMap<>();

        State flowchart = getFlowchart();

        collectLoopStates(statesToSplit, flowchart);
        collectMultipleRootStates(statesToSplit, flowchart);
        markLoops(statesToSplit);

        collectStates(stateMap, flowchart);
        prepareSwitchBlocks(stateMap, flowchart);
        combineBlocks(stateMap, flowchart);

        setJaxbState(stateMap.get(flowchart));
    }

    private void collectLoopStates(Map<State, Collection<State>> map, State root) {
        FlowchartContract callback = new FlowchartContractAdapter() {

            private final Set<State> visited = new HashSet<>();

            @Override
            public void accept(EntryState state) {
                acceptChainBlock(state);
            }

            @Override
            public void accept(Text text) {
                acceptChainBlock(text);
            }

            @Override
            public void accept(Reply reply) {
                acceptChainBlock(reply);
            }

            @Override
            public void accept(Option option) {
                acceptChainBlock(option);
            }

            @Override
            public void accept(ConditionalOption option) {
                acceptChainBlock(option);
            }

            @Override
            public void accept(Menu menu) {
                menu.branchesStream()
                        .forEach(option -> acceptBlock(menu, option));
            }

            @Override
            public void accept(Switch aSwitch) {
                acceptBlock(aSwitch, aSwitch.getTrueBranch());
                acceptBlock(aSwitch, aSwitch.getFalseBranch());
            }

            private void acceptChainBlock(ChainState state) {
                acceptBlock(state, state.getNext());
            }

            private void acceptBlock(State root, State child) {
                if (root.getRoots().length > 1 && !visited.contains(child)) {
                    Set<State> visitedState = new HashSet<>(visited);
                    boolean res = collectLoopStates(root, child, map, visitedState);

                    if (res) {
                        visited.addAll(visitedState);
                    }
                }
            }
        };

        runMouse(root, callback);
    }

    private void collectMultipleRootStates(Map<State, Collection<State>> map, State root) {
        FlowchartContract callback = new SimpleFlowchartContract() {

            @Override
            public void acceptState(State state) {
                State[] roots = state.getRoots();

                if (roots.length > 1) {
                    map.putIfAbsent(state, new HashSet<>());
                    Collection<State> mappedRoots = map.get(state);

                    State randomFixedRoot = Stream.of(roots)
                            .filter(root -> !mappedRoots.contains(root))
                            .findAny()
                            .orElse(null);

                    mappedRoots.addAll(Arrays.asList(roots));
                    mappedRoots.remove(randomFixedRoot);
                }
            }
        };

        runMouse(root, callback);
    }

    private void markLoops(Map<State, Collection<State>> map) {
        AtomicInteger ref = new AtomicInteger();

        map.forEach((mainState, roots) -> {
            int index = ref.getAndIncrement();

            roots.forEach(root -> {
                CycleEntryState entryState = new CycleEntryState(String.format("#%d", index));

                FlowchartContract callback = new FlowchartContractAdapter() {
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
                    public void accept(Switch aSwitch) {
                        if (aSwitch.getTrueBranch() == mainState) {
                            acceptState(aSwitch::setTrueBranch);
                        }

                        if (aSwitch.getFalseBranch() == mainState) {
                            acceptState(aSwitch::setFalseBranch);
                        }
                    }

                    private void acceptChainState(ChainState state) {
                        acceptState(state::setNext);
                    }

                    private void acceptState(Consumer<State> consumer) {
                        consumer.accept(entryState);
                    }
                };

                root.accept(callback);
            });

            FlowchartContract callback = new FlowchartContractAdapter() {
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
                public void accept(Switch aSwitch) {
                    if (aSwitch.getTrueBranch() == mainState) {
                        acceptState(aSwitch.getTrueBranch(), aSwitch::setTrueBranch);
                    }

                    if (aSwitch.getFalseBranch() == mainState) {
                        acceptState(aSwitch.getFalseBranch(), aSwitch::setFalseBranch);
                    }
                }

                private void acceptChainState(ChainState state) {
                    acceptState(state.getNext(), state::setNext);
                }

                private void acceptState(State state, Consumer<State> consumer) {
                    CycleMarker marker = new CycleMarker(String.format("#%d", index));

                    consumer.accept(marker);
                    marker.setNext(state);
                }
            };

            Stream.of(mainState.getRoots())
                    .forEach(root -> root.accept(callback));
        });
    }

    private void collectStates(Map<State, JaxbState> stateMap, State flowchart) {
        FlowchartContract contract = new FlowchartContract() {
            @Override
            public void accept(EntryState state) {
                stateMap.put(state, new JaxbRootState());
            }

            @Override
            public void accept(CycleMarker marker) {
                stateMap.put(marker, new JaxbMarker(marker.getValue()));
            }

            @Override
            public void accept(CycleEntryState state) {
                stateMap.put(state, new JaxbGoToState(state.getValue()));
            }

            @Override
            public void accept(Text text) {
                stateMap.put(text, new JaxbText(text.getValue()));
            }

            @Override
            public void accept(Reply reply) {
                stateMap.put(reply, new JaxbReply(reply.getCharacter(), reply.getLine()));
            }

            @Override
            public void accept(Option option) {
                stateMap.put(option, new JaxbOption(option.getValue(), null));
            }

            @Override
            public void accept(ConditionalOption option) {
                stateMap.put(option, new JaxbOption(option.getValue(), option.getCondition()));
            }

            @Override
            public void accept(Menu menu) {
                stateMap.put(menu, new JaxbMenu());
            }

            @Override
            public void accept(Switch aSwitch) {
                stateMap.put(aSwitch, new JaxbComplexSwitch());
            }

            @Override
            public void accept(EndPoint point) {
                stateMap.put(point, new JaxbEndState());
            }
        };

        runMouse(flowchart, contract);
    }

    private void prepareSwitchBlocks(Map<State, JaxbState> stateMap, State flowchart) {
        FlowchartContract contract = new FlowchartContractAdapter() {

            @Override
            public void accept(Switch aSwitch) {
                JaxbComplexSwitch jaxbComplexSwitch = (JaxbComplexSwitch) stateMap.get(aSwitch);

                jaxbComplexSwitch.addChild(new JaxbSwitchBranch(aSwitch.getCondition()));
                jaxbComplexSwitch.addChild(new JaxbSwitchBranch());
            }
        };

        runMouse(flowchart, contract);
    }

    private void combineBlocks(Map<State, JaxbState> stateMap, State flowchart) {
        FlowchartContract contract = new FlowchartContractAdapter() {

            @Override
            public void accept(EntryState state) {
                acceptComplexState((JaxbRootState) stateMap.get(state), state);
            }

            @Override
            public void accept(Option option) {
                acceptComplexState((JaxbOption) stateMap.get(option), option);
            }

            @Override
            public void accept(ConditionalOption option) {
                acceptComplexState((JaxbOption) stateMap.get(option), option);
            }

            @Override
            public void accept(Menu menu) {
                JaxbMenu jaxbMenu = (JaxbMenu) stateMap.get(menu);

                menu.branchesStream().forEach(option ->
                        jaxbMenu.addChild(stateMap.get(option)));
            }

            @Override
            public void accept(Switch aSwitch) {
                JaxbComplexSwitch jaxbComplexSwitch = (JaxbComplexSwitch) stateMap.get(aSwitch);

                if (aSwitch.getTrueBranch() != null) {
                    JaxbSwitchBranch branch = (JaxbSwitchBranch) jaxbComplexSwitch.childAt(0);

                    branch.addChild(stateMap.get(aSwitch.getTrueBranch()));
                    acceptComplexState(branch, aSwitch.getTrueBranch());
                }

                if (aSwitch.getFalseBranch() != null) {
                    JaxbSwitchBranch branch = (JaxbSwitchBranch) jaxbComplexSwitch.childAt(1);

                    branch.addChild(stateMap.get(aSwitch.getFalseBranch()));
                    acceptComplexState(branch, aSwitch.getFalseBranch());
                }
            }

            private void acceptComplexState(JaxbComplexState complexState, State state) {
                if (!(state instanceof ChainState)) {
                    return;
                }

                State next = ((ChainState) state).getNext();

                while (next instanceof ChainState) {
                    complexState.addChild(stateMap.get(next));
                    next = ((ChainState) next).getNext();
                }

                if (next != null) {
                    complexState.addChild(stateMap.get(next));
                }
            }
        };

        runMouse(flowchart, contract);
    }

    private boolean collectLoopStates(State root, State child, Map<State, Collection<State>> map,
                                      Set<State> visited) {
        AtomicBoolean result = new AtomicBoolean();

        FlowchartContract callback = new FlowchartContractAdapter() {

            @Override
            public void accept(EntryState state) {
                acceptChainBlock(state);
            }

            @Override
            public void accept(Text text) {
                acceptChainBlock(text);
            }

            @Override
            public void accept(Reply reply) {
                acceptChainBlock(reply);
            }

            @Override
            public void accept(Option option) {
                acceptChainBlock(option);
            }

            @Override
            public void accept(ConditionalOption option) {
                acceptChainBlock(option);
            }

            @Override
            public void accept(Switch aSwitch) {
                accept(aSwitch, aSwitch.getTrueBranch());
                accept(aSwitch, aSwitch.getFalseBranch());
            }

            private void acceptChainBlock(ChainState block) {
                accept(block, block.getNext());
            }

            private void accept(State rootState, State childBlock) {
                if (childBlock == root) {
                    map.computeIfAbsent(root, i -> new HashSet<>()).add(rootState);
                    result.set(true);
                }

                visited.add(rootState);
            }
        };

        runMouse(child, callback);
        return result.get();
    }

    private void runMouse(State state, FlowchartContract contract) {
        FlowchartContract mouse = new FlowchartMouse(contract,
                new ParentFirstFlowchartMouseStrategy());

        state.accept(mouse);
    }
}
