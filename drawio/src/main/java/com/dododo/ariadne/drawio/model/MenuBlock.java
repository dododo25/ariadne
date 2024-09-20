package com.dododo.ariadne.drawio.model;

import com.dododo.ariadne.drawio.factory.BlockComparatorFactory;
import com.dododo.ariadne.drawio.factory.MenuBlockComparatorFactory;
import com.dododo.ariadne.drawio.contract.BlockFlowchartContract;
import com.dododo.ariadne.mxg.model.DiagramRoot;
import com.dododo.ariadne.mxg.model.MxEdgeCell;
import com.dododo.ariadne.mxg.model.MxNodeCell;
import com.dododo.ariadne.mxg.geometry.ComplexNodeGeometry;
import com.dododo.ariadne.mxg.geometry.EdgeGeometry;
import com.dododo.ariadne.mxg.style.BooleanStyleParam;
import com.dododo.ariadne.mxg.style.ShapeStyleParam;
import com.dododo.ariadne.mxg.style.SingleKeyStyleParam;
import com.dododo.ariadne.mxg.style.StrokeColorStyleParam;
import com.dododo.ariadne.mxg.style.WhiteSpaceStyleParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class MenuBlock extends Block {

    private final List<OptionBlock> branches;

    private int width;

    public MenuBlock(int id) {
        super(id);
        this.branches = new ArrayList<>();
    }

    public void addBranch(OptionBlock block) {
        branches.add(block);

        if (block != null) {
            block.addRoot(this);
        }
    }

    public int branchesCount() {
        return branches.size();
    }

    public OptionBlock branchAt(int index) {
        return branches.get(index);
    }

    public Stream<OptionBlock> branchesStream() {
        return branches.stream();
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return 4;
    }

    @Override
    public BlockComparatorFactory getFactory() {
        return new MenuBlockComparatorFactory(this);
    }

    @Override
    public void accept(BlockFlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public void accept(DiagramRoot diagramRoot) {
        int height = getHeight();

        MxNodeCell groupNodeCell = new MxNodeCell.Builder()
                .setId(id)
                .addStyleParam(SingleKeyStyleParam.GROUP)
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.TREE_MOVING))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.CONNECTABLE))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.RESIZE_WIDTH))
                .setGeometry(ComplexNodeGeometry.create(x, y, width, height))
                .build();

        MxNodeCell firstPartNodeCell = new MxNodeCell.Builder()
                .setId(id)
                .setIdSuffix(".1")
                .setParent(id)
                .addStyleParam(WhiteSpaceStyleParam.WRAP)
                .addStyleParam(StrokeColorStyleParam.NONE)
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.TREE_MOVING))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.HTML))
                .addStyleParam(BooleanStyleParam.createAsFalse(BooleanStyleParam.Key.CONNECTABLE))
                .setGeometry(ComplexNodeGeometry.create(0, 0, width, height))
                .build();

        MxEdgeCell secondPartEdgeCell = new MxEdgeCell.NoVerticesBuilder()
                .setId(id)
                .setIdSuffix(".2")
                .setParent(id)
                .addStyleParam(ShapeStyleParam.LINK)
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.TREE_MOVING))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.HTML))
                .addStyleParam(BooleanStyleParam.createAsFalse(BooleanStyleParam.Key.ROUNDED))
                .setGeometry(EdgeGeometry.create(0, height / 2, width, height / 2))
                .build();

        diagramRoot.getCells().addAll(Arrays.asList(groupNodeCell, firstPartNodeCell, secondPartEdgeCell));

        branches.forEach(optionBlock -> prepareOptionBlockEdge(diagramRoot, optionBlock));
    }

    @Override
    public String toString() {
        return String.format("%s(id=%d, x=%d, y=%d, width=%d)", getClass().getSimpleName(), id, x, y, width);
    }

    private void prepareOptionBlockEdge(DiagramRoot root, Block block) {
        MxEdgeCell edgeCell = new MxEdgeCell.Builder()
                .setSource(id)
                .setTarget(block.id)
                .setEntryPoint(0.5, 0)
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.ROUNDED))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.ORTHOGONAL_LOOP))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.ORTHOGONAL))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.HTML))
                .build();

        root.getCells().add(edgeCell);
    }
}
