package com.dododo.ariadne.drawio.mouse;

import com.dododo.ariadne.drawio.contract.DrawIoFlowchartContract;
import com.dododo.ariadne.drawio.contract.DrawIoSimpleFlowchartContract;
import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.EndBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.StatementBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;
import com.dododo.ariadne.drawio.mouse.strategy.DrawIoChildrenFirstFlowchartMouseStrategy;
import com.dododo.ariadne.drawio.mouse.strategy.DrawIoFlowchartMouseStrategy;
import com.dododo.ariadne.drawio.mouse.strategy.DrawIoParentFirstFlowchartMouseStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DrawIoFlowchartMouseTest {

    private static EntryBlock entryBlock;
    private static StatementBlock statement1;
    private static StatementBlock statement2;
    private static SwitchBlock aSwitch;
    private static EndBlock endPoint1;
    private static EndBlock endPoint2;

    @BeforeAll
    static void setUp() {
        entryBlock = new EntryBlock(0);
        statement1 = new StatementBlock(1, "test1");
        statement2 = new StatementBlock(2, "test2");
        aSwitch = new SwitchBlock(7, "test");
        endPoint1 = new EndBlock(8);
        endPoint2 = new EndBlock(9);

        entryBlock.setNext(statement1);
        statement1.setNext(aSwitch);
        aSwitch.setTrueBranch(statement2);
        aSwitch.setFalseBranch(endPoint1);
        statement2.setNext(endPoint2);
    }

    @Test
    void testAcceptWhenParentFirstFlowchartMouseStrategyIsUsedShouldNotThrowException() {
        List<Block> expected = Arrays.asList(entryBlock, statement1, aSwitch, statement2, endPoint2, endPoint1);
        testAccept(expected, new DrawIoParentFirstFlowchartMouseStrategy());
    }

    @Test
    void testAcceptWhenChildrenFirstFlowchartMouseStrategyIsUsedShouldNotThrowException() {
        List<Block> expected = Arrays.asList(endPoint2, statement2, endPoint1, aSwitch, statement1, entryBlock);
        testAccept(expected, new DrawIoChildrenFirstFlowchartMouseStrategy());
    }

    private void testAccept(List<Block> expected, DrawIoFlowchartMouseStrategy strategy) {
        List<Block> blocks = new ArrayList<>();

        DrawIoFlowchartContract contract = new DrawIoSimpleFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                blocks.add(block);
            }
        };

        DrawIoFlowchartMouse mouse = new DrawIoFlowchartMouse(contract, strategy);
        entryBlock.accept(mouse);

        Assertions.assertEquals(expected, blocks);
    }
}
