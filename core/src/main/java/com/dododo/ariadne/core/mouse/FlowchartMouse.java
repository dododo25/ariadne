package com.dododo.ariadne.core.mouse;

import com.dododo.ariadne.core.contract.FlowchartContract;
import com.dododo.ariadne.core.model.ChainState;
import com.dododo.ariadne.core.model.EndPoint;
import com.dododo.ariadne.core.model.EntryState;
import com.dododo.ariadne.core.model.State;
import com.dododo.ariadne.core.model.Statement;
import com.dododo.ariadne.core.model.Switch;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class FlowchartMouse implements FlowchartContract {

    private final FlowchartContract callback;

    private final Set<State> visited;

    public FlowchartMouse(FlowchartContract callback) {
        this.callback = callback;
        this.visited = new HashSet<>();
    }

    @Override
    public void accept(EntryState state) {
        acceptChainState(state);
    }

    @Override
    public void accept(Statement statement) {
        acceptChainState(statement);
    }

    @Override
    public void accept(Switch aSwitch) {
        if (!visited.contains(aSwitch)) {
            visited.add(aSwitch);
            callback.accept(aSwitch);

            if (aSwitch.getTrueBranch() != null) {
                aSwitch.getTrueBranch().accept(this);
            }

            if (aSwitch.getFalseBranch() != null) {
                aSwitch.getFalseBranch().accept(this);
            }
        }
    }

    @Override
    public void accept(EndPoint point) {
        if (!visited.contains(point)) {
            visited.add(point);
            point.accept(callback);
        }
    }

    private void acceptChainState(ChainState state) {
        if (!visited.contains(state)) {
            LinkedList<ChainState> states = prepareChain(state);

            for (State s : states) {
                if (visited.contains(s)) {
                    break;
                }

                visited.add(s);
                s.accept(callback);
            }

            if (states.getLast().getNext() != null) {
                states.getLast().getNext().accept(this);
            }
        }
    }

    private static LinkedList<ChainState> prepareChain(ChainState state) {
        LinkedList<ChainState> result = new LinkedList<>();
        State s = state;

        while (s instanceof ChainState) {
            if (result.contains(s)) {
                break;
            }

            result.addLast((ChainState) s);
            s = ((ChainState) s).getNext();
        }

        return result;
    }
}
