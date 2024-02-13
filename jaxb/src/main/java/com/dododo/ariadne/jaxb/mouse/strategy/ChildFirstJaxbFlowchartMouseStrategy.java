package com.dododo.ariadne.jaxb.mouse.strategy;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContractAdapter;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbMenu;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;

import java.util.Collection;

public class ChildFirstJaxbFlowchartMouseStrategy implements JaxbFlowchartMouseStrategy {

    @Override
    public void acceptSingleState(JaxbState state, JaxbFlowchartContract callback, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates) {
        grayStates.remove(state);

        if (blackStates.contains(state)) {
            return;
        }

        blackStates.add(state);
        state.accept(callback);

        acceptRoots(state, grayStates, blackStates);
    }

    @Override
    public void acceptComplexState(JaxbComplexState complexState, JaxbFlowchartContract callback, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates) {
        grayStates.remove(complexState);

        if (blackStates.contains(complexState)) {
            return;
        }

        blackStates.add(complexState);
        complexState.accept(callback);

        acceptRoots(complexState, grayStates, blackStates);
    }

    protected void acceptRoots(JaxbState state, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates) {
        if (state.getRoot() == null) {
            return;
        }

        state.getRoot().accept(new InnerJaxbFlowchartContract(grayStates, blackStates));
    }

    protected static class InnerJaxbFlowchartContract extends JaxbFlowchartContractAdapter {

        protected final Collection<JaxbState> grayStates;

        protected final Collection<JaxbState> blackStates;

        protected InnerJaxbFlowchartContract(Collection<JaxbState> grayStates, Collection<JaxbState> blackStates) {
            this.grayStates = grayStates;
            this.blackStates = blackStates;
        }

        @Override
        public void accept(JaxbRootState state) {
            acceptComplexState(state);
        }

        @Override
        public void accept(JaxbMenu menu) {
            acceptComplexState(menu);
        }

        @Override
        public void accept(JaxbOption option) {
            acceptComplexState(option);
        }

        @Override
        public void accept(JaxbComplexSwitch complexSwitch) {
            acceptComplexState(complexSwitch);
        }

        @Override
        public void accept(JaxbSwitchBranch switchBranch) {
            acceptComplexState(switchBranch);
        }

        protected void acceptComplexState(JaxbComplexState state) {
            if (state.childrenCount() == 0 || state.childrenStream().allMatch(blackStates::contains)) {
                grayStates.add(state);
            }
        }
    }
}
