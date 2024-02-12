package com.dododo.ariadne.mxg.common.mouse;

import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.ConditionalOptionBlock;
import com.dododo.ariadne.mxg.common.model.EndBlock;
import com.dododo.ariadne.mxg.common.model.EntryBlock;
import com.dododo.ariadne.mxg.common.model.MenuBlock;
import com.dododo.ariadne.mxg.common.model.OptionBlock;
import com.dododo.ariadne.mxg.common.model.ReplyBlock;
import com.dododo.ariadne.mxg.common.model.SwitchBlock;
import com.dododo.ariadne.mxg.common.model.TextBlock;
import com.dododo.ariadne.mxg.common.mouse.BlockFlowchartMouse;
import com.dododo.ariadne.mxg.common.mouse.ParentFirstBlockFlowchartMouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ParentFirstBlockFlowchartMouseTest {

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
        List<Block> expected = Arrays.asList(entryBlock, reply, menu, option1, option2, text1, aSwitch, text2,
                endPoint1, endPoint2);
        List<Block> blocks = new ArrayList<>();

        BlockFlowchartMouse mouse = new ParentFirstBlockFlowchartMouse();

        mouse.accept(entryBlock, blocks::add);
        Assertions.assertEquals(expected, blocks);
    }
}
