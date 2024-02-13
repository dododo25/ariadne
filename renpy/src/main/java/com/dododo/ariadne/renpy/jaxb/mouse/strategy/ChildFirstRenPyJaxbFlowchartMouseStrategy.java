package com.dododo.ariadne.renpy.jaxb.mouse.strategy;

import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.mouse.strategy.ChildFirstJaxbFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.model.JaxbCallToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbRenPyMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;

import java.util.Collection;

public class ChildFirstRenPyJaxbFlowchartMouseStrategy extends ChildFirstJaxbFlowchartMouseStrategy {

    @Override
    protected void acceptRoots(JaxbState state, Collection<JaxbState> grayStates, Collection<JaxbState> blackStates) {
        if (state.getRoot() == null) {
            return;
        }

        state.getRoot().accept(new InnerRenPyJaxbFlowchartContract(grayStates, blackStates));
    }

    protected static class InnerRenPyJaxbFlowchartContract extends InnerJaxbFlowchartContract
            implements RenPyJaxbFlowchartContract {

        protected InnerRenPyJaxbFlowchartContract(Collection<JaxbState> grayStates, Collection<JaxbState> blackStates) {
            super(grayStates, blackStates);
        }

        @Override
        public void accept(JaxbInitGroupState state) {
            acceptComplexState(state);
        }

        @Override
        public void accept(JaxbRenPyMenu menu) {
            acceptComplexState(menu);
        }

        @Override
        public void accept(JaxbSwitchFalseBranch switchBranch) {
            acceptComplexState(switchBranch);
        }

        @Override
        public void accept(JaxbLabelledGroup group) {
            acceptComplexState(group);
        }

        @Override
        public void accept(JaxbCallToState state) {
            // no use
        }
    }
}
