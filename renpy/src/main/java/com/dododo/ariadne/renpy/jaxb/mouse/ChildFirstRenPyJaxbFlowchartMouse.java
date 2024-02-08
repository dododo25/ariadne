package com.dododo.ariadne.renpy.jaxb.mouse;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.mouse.ChildFirstJaxbFlowchartMouse;
import com.dododo.ariadne.jaxb.mouse.JaxbFlowchartMouse;
import com.dododo.ariadne.jaxb.mouse.strategy.ChildFirstJaxbFlowchartMouseStrategy;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.contract.RenPyJaxbSimpleFlowchartContract;
import com.dododo.ariadne.renpy.jaxb.model.JaxbCallToState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbInitGroupState;
import com.dododo.ariadne.renpy.jaxb.model.JaxbLabelledGroup;
import com.dododo.ariadne.renpy.jaxb.model.JaxbMenu;
import com.dododo.ariadne.renpy.jaxb.model.JaxbSwitchFalseBranch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class ChildFirstRenPyJaxbFlowchartMouse extends ChildFirstJaxbFlowchartMouse {

    public ChildFirstRenPyJaxbFlowchartMouse() {
        super(new ChildFirstJaxbFlowchartMouseStrategy());
    }

    @Override
    public void accept(JaxbState state, Consumer<JaxbState> consumer) {
        JaxbFlowchartContract callback = new RenPyJaxbSimpleFlowchartContract() {
            @Override
            public void acceptState(JaxbState state) {
                consumer.accept(state);
            }
        };

        accept(state, callback);
    }

    @Override
    protected Collection<JaxbState> prepareStartingPoints(JaxbState state) {
        Collection<JaxbState> result = new ArrayList<>();

        JaxbFlowchartContract callback = new InnerRenPyJaxbFlowchartContract(result);
        JaxbFlowchartMouse mouse = new ParentFirstRenPyJaxbFlowchartMouse();

        mouse.accept(state, callback);

        return result;
    }

    protected static class InnerRenPyJaxbFlowchartContract extends InnerJaxbFlowchartContract
            implements RenPyJaxbFlowchartContract {

        public InnerRenPyJaxbFlowchartContract(Collection<JaxbState> result) {
            super(result);
        }

        @Override
        public void accept(JaxbInitGroupState state) {
            acceptComplexState(state);
        }

        @Override
        public void accept(JaxbMenu menu) {
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
            acceptSingleState(state);
        }
    }
}
