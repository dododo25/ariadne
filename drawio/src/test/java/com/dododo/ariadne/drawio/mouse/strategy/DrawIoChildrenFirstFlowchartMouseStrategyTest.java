package com.dododo.ariadne.drawio.mouse.strategy;

import com.dododo.ariadne.drawio.contract.DrawIoFlowchartContract;
import com.dododo.ariadne.drawio.contract.DrawIoSimpleFlowchartContract;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.EndBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;
import com.dododo.ariadne.drawio.mouse.DrawIoFlowchartMouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiConsumer;

class DrawIoChildrenFirstFlowchartMouseStrategyTest {

    private static DrawIoFlowchartMouseStrategy strategy;

    @BeforeAll
    static void setUp() {
        strategy = new DrawIoChildrenFirstFlowchartMouseStrategy();
    }

    @Test
    void testAcceptChainStateShouldDoneWell() {
        EntryBlock first = new EntryBlock(0);
        EntryBlock second = new EntryBlock(1);
        EndBlock point = new EndBlock(2);

        List<Block> expected = Arrays.asList(point, second, first);

        first.setNext(second);
        second.setNext(point);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptChainBlock(first, mouse, callback, new HashSet<>()));
    }

    @Test
    void testAcceptSwitchWhenTrueBranchExistsShouldDoneWell() {
        SwitchBlock aSwitch = new SwitchBlock(0, "test");
        EntryBlock state = new EntryBlock(1);

        List<Block> expected = Arrays.asList(state, aSwitch);

        aSwitch.setTrueBranch(state);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptSwitchBlock(aSwitch, mouse, callback, new HashSet<>()));
    }

    @Test
    void testAcceptSwitchWhenFalseBranchExistsShouldDoneWell() {
        SwitchBlock aSwitch = new SwitchBlock(0, "test");
        EntryBlock state = new EntryBlock(1);

        List<Block> expected = Arrays.asList(state, aSwitch);

        aSwitch.setFalseBranch(state);

        testAccept(expected, (callback, mouse) ->
                strategy.acceptSwitchBlock(aSwitch, mouse, callback, new HashSet<>()));
    }

    private void testAccept(List<Block> expected, BiConsumer<DrawIoFlowchartContract, DrawIoFlowchartMouse> consumer) {
        List<Block> actual = new ArrayList<>();

        DrawIoFlowchartContract callback = new DrawIoSimpleFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                actual.add(block);
            }
        };

        DrawIoFlowchartMouse mouse = new DrawIoFlowchartMouse(callback, strategy);

        consumer.accept(callback, mouse);
        Assertions.assertEquals(expected, actual);
    }
}
