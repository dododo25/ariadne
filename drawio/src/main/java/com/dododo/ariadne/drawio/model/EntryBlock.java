package com.dododo.ariadne.drawio.model;

import com.dododo.ariadne.drawio.contract.DrawIoFlowchartContract;
import com.dododo.ariadne.drawio.factory.BlockComparatorFactory;
import com.dododo.ariadne.drawio.factory.EntryBlockComparatorFactory;
import com.dododo.ariadne.drawio.mxg.DiagramRoot;
import com.dododo.ariadne.drawio.mxg.geometry.ComplexNodeGeometry;
import com.dododo.ariadne.drawio.mxg.MxNodeCell;
import com.dododo.ariadne.drawio.mxg.style.AspectStyleParam;
import com.dododo.ariadne.drawio.mxg.style.BooleanStyleParam;
import com.dododo.ariadne.drawio.mxg.style.SingleKeyStyleParam;
import com.dododo.ariadne.drawio.mxg.style.WhiteSpaceStyleParam;

public final class EntryBlock extends ChainBlock {

    public EntryBlock(int id) {
        super(id);
    }

    @Override
    public void accept(DrawIoFlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public int getWidth() {
        return 40;
    }

    @Override
    public int getHeight() {
        return 40;
    }

    @Override
    public BlockComparatorFactory getFactory() {
        return new EntryBlockComparatorFactory(this);
    }

    @Override
    public void accept(DiagramRoot diagramRoot) {
        int width = getWidth();
        int height = getHeight();

        MxNodeCell nodeCell = new MxNodeCell.Builder()
                .setId(id)
                .addStyleParam(WhiteSpaceStyleParam.WRAP)
                .addStyleParam(AspectStyleParam.FIXED)
                .addStyleParam(SingleKeyStyleParam.ELLIPSE)
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.HTML))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.TREE_MOVING))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.CONNECTABLE))
                .addStyleParam(BooleanStyleParam.createAsFalse(BooleanStyleParam.Key.EDITABLE))
                .addStyleParam(BooleanStyleParam.createAsFalse(BooleanStyleParam.Key.RESIZABLE))
                .setGeometry(ComplexNodeGeometry.create(x, y, width, height))
                .build();

        diagramRoot.getCells().add(nodeCell);
        addEdge(diagramRoot);
    }
}
