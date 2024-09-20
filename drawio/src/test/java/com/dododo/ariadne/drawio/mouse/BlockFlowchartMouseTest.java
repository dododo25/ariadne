package com.dododo.ariadne.drawio.mouse;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.ConditionalOptionBlock;
import com.dododo.ariadne.drawio.model.EndBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.MenuBlock;
import com.dododo.ariadne.drawio.model.OptionBlock;
import com.dododo.ariadne.drawio.model.ReplyBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;
import com.dododo.ariadne.drawio.model.TextBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BlockFlowchartMouseTest {

    private static BlockFlowchartMouse mouse;

    @BeforeAll
    static void setUp() {
        mouse = new BlockFlowchartMouse();
    }

    @Test
    void testAcceptShouldDoneWell() {
        testAccept(new EntryBlock(0));
        testAccept(new TextBlock(0, "test"));
        testAccept(new ReplyBlock(0, "character_test", "test"));
        testAccept(new MenuBlock(0));
        testAccept(new OptionBlock(0, "test"));
        testAccept(new ConditionalOptionBlock(0, "test", "test_conditional"));
        testAccept(new SwitchBlock(0, "test"));
        testAccept(new EndBlock(0));
    }

    private void testAccept(Block block) {
        mouse.accept(block, s -> Assertions.assertSame(s, block));
    }
}
