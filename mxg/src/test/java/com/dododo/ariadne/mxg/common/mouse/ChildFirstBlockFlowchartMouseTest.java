package com.dododo.ariadne.mxg.common.mouse;

import com.dododo.ariadne.mxg.common.contract.BlockFlowchartContract;
import com.dododo.ariadne.mxg.common.contract.SimpleBlockFlowchartContract;
import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.ConditionalOptionBlock;
import com.dododo.ariadne.mxg.common.model.EndBlock;
import com.dododo.ariadne.mxg.common.model.EntryBlock;
import com.dododo.ariadne.mxg.common.model.MenuBlock;
import com.dododo.ariadne.mxg.common.model.OptionBlock;
import com.dododo.ariadne.mxg.common.model.ReplyBlock;
import com.dododo.ariadne.mxg.common.model.SwitchBlock;
import com.dododo.ariadne.mxg.common.model.TextBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiConsumer;

class ChildFirstBlockFlowchartMouseTest {

    private static BlockFlowchartMouse mouse;

    @BeforeAll
    static void setUp() {
        mouse = new ChildFirstBlockFlowchartMouse();
    }

    @Test
    void testAcceptShouldDoneWell() {
        testAccept(new EntryBlock(0));
        testAccept(new TextBlock(0, "test"));
        testAccept(new ReplyBlock(0, null, "test"));
        testAccept(new MenuBlock(0));
        testAccept(new OptionBlock(0, "test"));
        testAccept(new ConditionalOptionBlock(0, "test", "test"));
        testAccept(new SwitchBlock(0, "test"));
        testAccept(new EndBlock(0));

        testAccept(new SwitchBlock(0, "test"), new EndBlock(1), SwitchBlock::setTrueBranch);
        testAccept(new SwitchBlock(0, "test"), new EndBlock(1), SwitchBlock::setFalseBranch);
    }

    private void testAccept(Block block) {
        mouse.accept(block, s -> Assertions.assertSame(s, block));
    }

    private <T extends Block> void testAccept(T first, Block second, BiConsumer<T, Block> consumer) {
        Collection<Block> expected = Arrays.asList(second, first);
        Collection<Block> states = new ArrayList<>();

        BlockFlowchartContract contract = new SimpleBlockFlowchartContract() {
            @Override
            public void acceptBlock(Block block) {
                states.add(block);
            }
        };

        consumer.accept(first, second);
        mouse.accept(first, contract);

        Assertions.assertEquals(expected, states);
    }
}
