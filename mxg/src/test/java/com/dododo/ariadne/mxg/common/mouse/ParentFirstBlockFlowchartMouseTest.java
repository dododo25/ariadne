package com.dododo.ariadne.mxg.common.mouse;

import com.dododo.ariadne.mxg.common.model.Block;
import com.dododo.ariadne.mxg.common.model.EndBlock;
import com.dododo.ariadne.mxg.common.model.EntryBlock;
import com.dododo.ariadne.mxg.common.model.SwitchBlock;
import com.dododo.ariadne.mxg.common.model.TextBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParentFirstBlockFlowchartMouseTest {

    private static BlockFlowchartMouse mouse;

    @BeforeAll
    static void setUp() {
        mouse = new ParentFirstBlockFlowchartMouse();
    }

    @Test
    void testAcceptShouldDoneWell() {
        testAccept(new EntryBlock(0));
        testAccept(new TextBlock(0, "test"));
        testAccept(new SwitchBlock(0, "test"));
        testAccept(new EndBlock(0));
    }

    private void testAccept(Block block) {
        mouse.accept(block, s -> Assertions.assertSame(s, block));
    }
}
