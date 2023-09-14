package com.dododo.ariadne.block.mouse;

import com.dododo.ariadne.block.contract.BlockFlowchartContract;
import com.dododo.ariadne.block.contract.BlockSimpleFlowchartContract;
import com.dododo.ariadne.block.model.Block;
import com.dododo.ariadne.block.model.ConditionalOptionBlock;
import com.dododo.ariadne.block.model.EndBlock;
import com.dododo.ariadne.block.model.EntryBlock;
import com.dododo.ariadne.block.model.MenuBlock;
import com.dododo.ariadne.block.model.OptionBlock;
import com.dododo.ariadne.block.model.ReplyBlock;
import com.dododo.ariadne.block.model.SwitchBlock;
import com.dododo.ariadne.block.model.TextBlock;
import com.dododo.ariadne.block.mouse.strategy.ChildrenFirstBlockFlowchartMouseStrategy;
import com.dododo.ariadne.block.mouse.strategy.BlockFlowchartMouseStrategy;
import com.dododo.ariadne.block.mouse.strategy.ParentFirstBlockFlowchartMouseStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BlockFlowchartMouseTest {

    private static EntryBlock entryBlock;
    private static TextBlock text1;
    private static TextBlock text2;
    private static ReplyBlock reply;
    private static MenuBlock menu;
    private static OptionBlock option1;
    private static ConditionalOptionBlock option2;
    private static SwitchBlock aSwitch;
    private static EndBlock endPoint1;
    private static EndBlock endPoint2;

    @BeforeAll
    static void setUp() {
        entryBlock = new EntryBlock(0);
        text1 = new TextBlock(1, "test1");
        text2 = new TextBlock(2, "test2");
        reply = new ReplyBlock(3, null, "test");
        menu = new MenuBlock(4);
        option1 = new OptionBlock(5, "test");
        option2 = new ConditionalOptionBlock(6, "test1", "test2");
        aSwitch = new SwitchBlock(7, "test");
        endPoint1 = new EndBlock(8);
        endPoint2 = new EndBlock(9);

        entryBlock.setNext(reply);
        reply.setNext(menu);
        menu.addBranch(option1);
        menu.addBranch(option2);
        option1.setNext(text1);
        option2.setNext(aSwitch);
        text1.setNext(aSwitch);
        aSwitch.setTrueBranch(text2);
        aSwitch.setFalseBranch(endPoint1);
        text2.setNext(endPoint2);
    }

    @Test
    void testAcceptWhenParentFirstFlowchartMouseStrategyIsUsedShouldNotThrowException() {
        List<Block> expected = Arrays.asList(entryBlock, reply, menu, option1, text1, aSwitch,
                text2, endPoint2, endPoint1, option2);
        testAccept(expected, new ParentFirstBlockFlowchartMouseStrategy());
    }

    @Test
    void testAcceptWhenChildrenFirstFlowchartMouseStrategyIsUsedShouldNotThrowException() {
        List<Block> expected = Arrays.asList(endPoint2, text2, endPoint1, aSwitch, text1,
                option1, option2, menu, reply, entryBlock);
        testAccept(expected, new ChildrenFirstBlockFlowchartMouseStrategy());
    }

    private void testAccept(List<Block> expected, BlockFlowchartMouseStrategy strategy) {
        List<Block> blocks = new ArrayList<>();

        BlockFlowchartContract contract = new BlockSimpleFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                blocks.add(block);
            }
        };

        BlockFlowchartMouse mouse = new BlockFlowchartMouse(contract, strategy);
        entryBlock.accept(mouse);

        Assertions.assertEquals(expected, blocks);
    }
}
