package com.dododo.ariadne.drawio.test.supplier;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.ChainBlock;
import com.dododo.ariadne.drawio.model.EndBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.MenuBlock;
import com.dododo.ariadne.drawio.model.OptionBlock;
import com.dododo.ariadne.drawio.model.ConditionalOptionBlock;
import com.dododo.ariadne.drawio.model.ReplyBlock;
import com.dododo.ariadne.drawio.model.StatementBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;
import com.dododo.ariadne.test.annotation.RuleSetSupplier;
import com.dododo.ariadne.test.rule.NodeRule;
import com.dododo.ariadne.test.rule.RuleSet;
import org.xml.sax.Attributes;

public class DrawIoFlowchartRulesSupplier {

    @RuleSetSupplier
    public RuleSet createRuleForEntryBlock() {
        return createRuleForChainBlock(EntryBlock.class, (id, attrs) -> new EntryBlock(id));
    }

    @RuleSetSupplier
    public RuleSet createRuleForStatementBlock() {
        return createRuleForChainBlock(StatementBlock.class,
                (id, attrs) -> new StatementBlock(id, attrs.getValue("value")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForReplyBlock() {
        return createRuleForChainBlock(ReplyBlock.class,
                (id, attrs) -> new ReplyBlock(id, attrs.getValue("character"), attrs.getValue("line")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForOptionBlock() {
        return createRuleForChainBlock(OptionBlock.class,
                (id, attrs) -> new OptionBlock(id, attrs.getValue("value")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForConditionalOptionBlock() {
        return createRuleForChainBlock(ConditionalOptionBlock.class, (id, attrs) -> new ConditionalOptionBlock(id,
                attrs.getValue("value"), attrs.getValue("condition")));
    }

    @RuleSetSupplier
    public RuleSet createRuleForMenuBlock() {
        return new RuleSet.Builder(MenuBlock.class)
                .setNodeRule((id, attrs) -> {
                    MenuBlock block = new MenuBlock(id);

                    block.setX(getIntValue(attrs, "x"));
                    block.setY(getIntValue(attrs, "y"));
                    block.setWidth(getIntValue(attrs, "width"));

                    return block;
                })
                .setEdgeRule((o1, o2, attrs) -> ((MenuBlock) o1).addBranch((OptionBlock) o2))
                .build();
    }

    @RuleSetSupplier
    public RuleSet createRuleForSwitchBlock() {
        return new RuleSet.Builder(SwitchBlock.class)
                .setNodeRule((id, attrs) -> new SwitchBlock(id, attrs.getValue("condition")))
                .setEdgeRule((o1, o2, attrs) -> {
                    SwitchBlock aSwitch = (SwitchBlock) o1;
                    Block next = (Block) o2;

                    String switchBranch = attrs.getValue("switchBranch");

                    if (switchBranch == null) {
                        throw new NullPointerException("Unknown value for switchBranch attribute");
                    }

                    if (switchBranch.equals("true")) {
                        aSwitch.setTrueBranch(next);
                    } else if (switchBranch.equals("false")) {
                        aSwitch.setFalseBranch(next);
                    }
                })
                .build();
    }

    @RuleSetSupplier
    public RuleSet createRuleForEndBlock() {
        return new RuleSet.Builder(EndBlock.class)
                .setNodeRule(createNodeRuleForBlock((id, attrs) -> new EndBlock(id)))
                .build();
    }

    private RuleSet createRuleForChainBlock(Class<? extends ChainBlock> type, NodeRule rule) {
        return new RuleSet.Builder(type)
                .setNodeRule(createNodeRuleForBlock(rule))
                .setEdgeRule((o1, o2, attrs) -> ((ChainBlock) o1).setNext((Block) o2))
                .build();
    }

    private NodeRule createNodeRuleForBlock(NodeRule rule) {
        return (id, attrs) -> {
            Block block = (Block) rule.createState(id, attrs);

            block.setX(getIntValue(attrs, "x"));
            block.setY(getIntValue(attrs, "y"));

            return block;
        };
    }

    private static int getIntValue(Attributes attrs, String key) {
        String value = attrs.getValue(key);

        if (value == null) {
            return 0;
        }

        return Integer.parseInt(value);
    }
}
