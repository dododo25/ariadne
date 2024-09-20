package com.dododo.ariadne.drawio.model;

import com.dododo.ariadne.drawio.contract.BlockFlowchartContract;
import com.dododo.ariadne.drawio.factory.BlockComparatorFactory;
import com.dododo.ariadne.drawio.factory.SwitchBlockComparatorFactory;
import com.dododo.ariadne.mxg.model.DiagramRoot;
import com.dododo.ariadne.mxg.model.MxEdgeCell;
import com.dododo.ariadne.mxg.model.MxNodeCell;
import com.dododo.ariadne.mxg.geometry.ComplexNodeGeometry;
import com.dododo.ariadne.mxg.geometry.EdgeGeometry;
import com.dododo.ariadne.mxg.geometry.RelativeNodeGeometry;
import com.dododo.ariadne.mxg.style.AlignStyleParam;
import com.dododo.ariadne.mxg.style.BooleanStyleParam;
import com.dododo.ariadne.mxg.style.EdgeStyleParam;
import com.dododo.ariadne.mxg.style.SingleKeyStyleParam;
import com.dododo.ariadne.mxg.style.VerticalAlignStyleParam;
import com.dododo.ariadne.mxg.style.WhiteSpaceStyleParam;

import java.util.Arrays;

public final class SwitchBlock extends Block {

    private final String condition;

    private Block trueBranch;

    private Block falseBranch;

    public SwitchBlock(int id, String condition) {
        super(id);
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public Block getTrueBranch() {
        return trueBranch;
    }

    public void setTrueBranch(Block block) {
        this.trueBranch = block;

        if (this.trueBranch != null) {
            this.trueBranch.addRoot(this);
        }
    }

    public Block getFalseBranch() {
        return falseBranch;
    }

    public void setFalseBranch(Block block) {
        this.falseBranch = block;

        if (this.falseBranch != null) {
            this.falseBranch.addRoot(this);
        }
    }

    @Override
    public int getWidth() {
        return 120;
    }

    @Override
    public int getHeight() {
        return 120;
    }

    @Override
    public BlockComparatorFactory getFactory() {
        return new SwitchBlockComparatorFactory(this);
    }

    @Override
    public void accept(BlockFlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public void accept(DiagramRoot diagramRoot) {
        int width = getWidth();
        int height = getHeight();

        MxNodeCell nodeCell = new MxNodeCell.Builder()
                .setId(id)
                .setValue(condition)
                .addStyleParam(SingleKeyStyleParam.RHOMBUS)
                .addStyleParam(WhiteSpaceStyleParam.WRAP)
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.HTML))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.TREE_MOVING))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.CONNECTABLE))
                .addStyleParam(BooleanStyleParam.createAsFalse(BooleanStyleParam.Key.RESIZABLE))
                .setGeometry(ComplexNodeGeometry.create(x, y, width, height))
                .build();

        MxEdgeCell.Builder trueCheckEdgeBuilder = new MxEdgeCell.Builder()
                .setSource(id)
                .setTarget(trueBranch.id)
                .setIdSuffix("t")
                .setExitPoint(0, 0.5)
                .addStyleParam(EdgeStyleParam.ORTHOGONAL_EDGE_STYLE)
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.HTML))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.ORTHOGONAL_LOOP))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.ROUNDED))
                .setGeometry(EdgeGeometry.create(0, 0, 0, 120));

        MxEdgeCell.Builder falseCheckEdgeBuilder = new MxEdgeCell.Builder()
                .setSource(id)
                .setTarget(falseBranch.id)
                .setIdSuffix("f")
                .setExitPoint(1, 0.5)
                .addStyleParam(EdgeStyleParam.ORTHOGONAL_EDGE_STYLE)
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.HTML))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.ORTHOGONAL_LOOP))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.ROUNDED))
                .setGeometry(EdgeGeometry.create(0, 0, 160, 120));

        if (!(trueBranch instanceof MenuBlock) || trueBranch.roots.size() == 1) {
            trueCheckEdgeBuilder.setEntryPoint(0.5, 0);
        }

        if (!(falseBranch instanceof MenuBlock) || falseBranch.roots.size() == 1) {
            falseCheckEdgeBuilder.setEntryPoint(0.5, 0);
        }

        MxEdgeCell trueCheckEdge = trueCheckEdgeBuilder.build();
        MxEdgeCell falseCheckEdge = falseCheckEdgeBuilder.build();

        MxNodeCell trueCheckEdgeLabelCell = new MxNodeCell.ParentCellBuilder()
                .setId(trueCheckEdge, "tl")
                .setValue("true")
                .addStyleParam(SingleKeyStyleParam.EDGE_LABEL)
                .addStyleParam(EdgeStyleParam.ORTHOGONAL_EDGE_STYLE)
                .addStyleParam(AlignStyleParam.RIGHT)
                .addStyleParam(VerticalAlignStyleParam.TOP)
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.HTML))
                .addStyleParam(BooleanStyleParam.createAsFalse(BooleanStyleParam.Key.RESIZABLE))
                .setGeometry(RelativeNodeGeometry.create(-1, 0))
                .build();

        MxNodeCell falseCheckEdgeLabelCell = new MxNodeCell.ParentCellBuilder()
                .setId(falseCheckEdge, "fl")
                .setValue("false")
                .addStyleParam(SingleKeyStyleParam.EDGE_LABEL)
                .addStyleParam(EdgeStyleParam.ORTHOGONAL_EDGE_STYLE)
                .addStyleParam(AlignStyleParam.LEFT)
                .addStyleParam(VerticalAlignStyleParam.TOP)
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.HTML))
                .addStyleParam(BooleanStyleParam.createAsFalse(BooleanStyleParam.Key.RESIZABLE))
                .setGeometry(RelativeNodeGeometry.create(-1, 0))
                .build();

        diagramRoot.getCells().addAll(Arrays.asList(nodeCell, trueCheckEdge, falseCheckEdge,
                trueCheckEdgeLabelCell, falseCheckEdgeLabelCell));
    }

    @Override
    public String toString() {
        return String.format("%s(id=%d, x=%d, y=%d, condition='%s')",
                getClass().getSimpleName(), id, x, y, condition);
    }
}
