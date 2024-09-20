package com.dododo.ariadne.drawio.set;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.ChainBlock;
import com.dododo.ariadne.drawio.model.ConditionalOptionBlock;
import com.dododo.ariadne.drawio.model.EndBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.MenuBlock;
import com.dododo.ariadne.drawio.model.OptionBlock;
import com.dododo.ariadne.drawio.model.ReplyBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;
import com.dododo.ariadne.drawio.model.TextBlock;
import com.dododo.ariadne.test.annotation.EdgeRule;
import com.dododo.ariadne.test.annotation.NodeRule;
import com.dododo.ariadne.test.annotation.Ruleset;

@Ruleset
public class BlockFlowchartRuleset {

    @NodeRule(type = EntryBlock.class, params = {"id", "x", "y"})
    public Block createEntryBlock(int id, int x, int y) {
        return prepareBlock(new EntryBlock(id), x, y);
    }

    @NodeRule(type = TextBlock.class, params = {"id", "x", "y", "value"})
    public Block createText(int id, int x, int y, String value) {
        return prepareBlock(new TextBlock(id, value), x, y);
    }

    @NodeRule(type = ReplyBlock.class, params = {"id", "x", "y", "character", "line"})
    public Block createReply(int id, int x, int y, String character, String line) {
        return prepareBlock(new ReplyBlock(id, character, line), x, y);
    }

    @NodeRule(type = MenuBlock.class, params = {"id", "x", "y", "width"})
    public Block createMenu(int id, int x, int y, int width) {
        MenuBlock block = new MenuBlock(id);

        block.setX(x);
        block.setY(y);
        block.setWidth(width);

        return block;
    }

    @NodeRule(type = OptionBlock.class, params = {"id", "x", "y", "value"})
    public Block createOption(int id, int x, int y, String value) {
        return prepareBlock(new OptionBlock(id, value), x, y);
    }

    @NodeRule(type = ConditionalOptionBlock.class, params = {"id", "x", "y", "value", "condition"})
    public Block createConditionalOption(int id, int x, int y, String value, String condition) {
        return prepareBlock(new ConditionalOptionBlock(id, value, condition), x, y);
    }

    @NodeRule(type = SwitchBlock.class, params = {"id", "x", "y", "condition"})
    public Block createSwitch(int id, int x, int y, String condition) {
        return prepareBlock(new SwitchBlock(id, condition), x, y);
    }

    @NodeRule(type = EndBlock.class, params = {"id", "x", "y"})
    public Block createEndPoint(int id, int x, int y) {
        return prepareBlock(new EndBlock(id), x, y);
    }

    @EdgeRule(type = {
            EntryBlock.class,
            TextBlock.class,
            ReplyBlock.class,
            OptionBlock.class,
            ConditionalOptionBlock.class
    })
    public void joinChainBlock(Block s1, Block s2) {
        ((ChainBlock) s1).setNext(s2);
    }

    @EdgeRule(type = MenuBlock.class)
    public void joinMenu(Block s1, Block s2) {
        ((MenuBlock) s1).addBranch((OptionBlock) s2);
    }

    @EdgeRule(type = SwitchBlock.class, params = "switchBranch")
    public void joinSwitch(Block s1, Block s2, String switchBranch) {
        if (switchBranch == null) {
            throw new NullPointerException("Unknown value for switchBranch attribute");
        }

        if (switchBranch.equals("true")) {
            ((SwitchBlock) s1).setTrueBranch(s2);
        } else if (switchBranch.equals("false")) {
            ((SwitchBlock) s1).setFalseBranch(s2);
        } else {
            throw new IllegalArgumentException("Illegal value for switchBranch attribute");
        }
    }

    private Block prepareBlock(Block block, int x, int y) {
        block.setX(x);
        block.setY(y);

        return block;
    }
}
