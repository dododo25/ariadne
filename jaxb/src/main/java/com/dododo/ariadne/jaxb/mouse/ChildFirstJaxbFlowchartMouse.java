package com.dododo.ariadne.jaxb.mouse;

import com.dododo.ariadne.jaxb.contract.JaxbFlowchartContract;
import com.dododo.ariadne.jaxb.contract.JaxbSimpleFlowchartContract;
import com.dododo.ariadne.jaxb.model.JaxbComplexState;
import com.dododo.ariadne.jaxb.model.JaxbComplexSwitch;
import com.dododo.ariadne.jaxb.model.JaxbEndState;
import com.dododo.ariadne.jaxb.model.JaxbGoToState;
import com.dododo.ariadne.jaxb.model.JaxbLabel;
import com.dododo.ariadne.jaxb.model.JaxbMenu;
import com.dododo.ariadne.jaxb.model.JaxbOption;
import com.dododo.ariadne.jaxb.model.JaxbPassState;
import com.dododo.ariadne.jaxb.model.JaxbReply;
import com.dododo.ariadne.jaxb.model.JaxbRootState;
import com.dododo.ariadne.jaxb.model.JaxbState;
import com.dododo.ariadne.jaxb.model.JaxbSwitchBranch;
import com.dododo.ariadne.jaxb.model.JaxbText;
import com.dododo.ariadne.jaxb.mouse.strategy.ChildFirstJaxbFlowchartMouseStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class ChildFirstJaxbFlowchartMouse extends JaxbFlowchartMouse {

    public ChildFirstJaxbFlowchartMouse() {
        this(new ChildFirstJaxbFlowchartMouseStrategy());
    }

    protected ChildFirstJaxbFlowchartMouse(ChildFirstJaxbFlowchartMouseStrategy strategy) {
        super(strategy);
    }

    @Override
    public void accept(JaxbState state, Consumer<JaxbState> consumer) {
        JaxbFlowchartContract callback = new JaxbSimpleFlowchartContract() {
            @Override
            public void acceptState(JaxbState state) {
                consumer.accept(state);
            }
        };

        accept(state, callback);
    }

    @Override
    public void accept(JaxbState state, JaxbFlowchartContract callback) {
        Collection<JaxbState> grayStates = prepareStartingPoints(state);
        Collection<JaxbState> blackStates = new ArrayList<>();

        while (!grayStates.isEmpty()) {
            grayStates.stream()
                    .findFirst()
                    .ifPresent(nextState -> nextState.accept(strategy, callback, grayStates, blackStates));
        }
    }

    protected Collection<JaxbState> prepareStartingPoints(JaxbState state) {
        Collection<JaxbState> result = new ArrayList<>();

        JaxbFlowchartContract callback = new InnerJaxbFlowchartContract(result);
        ParentFirstJaxbFlowchartMouse mouse = new ParentFirstJaxbFlowchartMouse();

        mouse.accept(state, callback);

        return result;
    }

    protected static class InnerJaxbFlowchartContract implements JaxbFlowchartContract {

        protected final Collection<JaxbState> result;

        public InnerJaxbFlowchartContract(Collection<JaxbState> result) {
            this.result = result;
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

        @Override
        public void accept(JaxbText text) {
            acceptSingleState(text);
        }

        @Override
        public void accept(JaxbReply reply) {
            acceptSingleState(reply);
        }

        @Override
        public void accept(JaxbLabel label) {
            acceptSingleState(label);
        }

        @Override
        public void accept(JaxbGoToState state) {
            acceptSingleState(state);
        }

        @Override
        public void accept(JaxbPassState state) {
            acceptSingleState(state);
        }

        @Override
        public void accept(JaxbEndState state) {
            acceptSingleState(state);
        }

        protected void acceptComplexState(JaxbComplexState state) {
            if (state.childrenCount() == 0) {
                result.add(state);
            }
        }

        protected void acceptSingleState(JaxbState state) {
            result.add(state);
        }
    }
}
