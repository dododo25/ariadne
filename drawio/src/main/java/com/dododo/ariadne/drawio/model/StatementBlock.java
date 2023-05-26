package com.dododo.ariadne.drawio.model;

import com.dododo.ariadne.drawio.contract.DrawIoFlowchartContract;
import com.dododo.ariadne.drawio.factory.BlockComparatorFactory;
import com.dododo.ariadne.drawio.factory.StatementBlockComparatorFactory;
import com.dododo.ariadne.drawio.mxg.DiagramRoot;
import com.dododo.ariadne.drawio.mxg.geometry.ComplexNodeGeometry;
import com.dododo.ariadne.drawio.mxg.MxNodeCell;
import com.dododo.ariadne.drawio.mxg.style.BooleanStyleParam;
import com.dododo.ariadne.drawio.mxg.style.WhiteSpaceStyleParam;

public final class StatementBlock extends SimpleBlock {

    public StatementBlock(int id, String value) {
        super(id, value);
    }

    @Override
    public int getWidth() {
        return 120;
    }

    @Override
    public int getHeight() {
        return 60;
    }

    @Override
    public BlockComparatorFactory getFactory() {
        return new StatementBlockComparatorFactory(this);
    }

    @Override
    public void accept(DrawIoFlowchartContract contract) {
        contract.accept(this);
    }

    @Override
    public void accept(DiagramRoot diagramRoot) {
        int width = getWidth();
        int height = getHeight();

        MxNodeCell nodeCell = new MxNodeCell.Builder()
                .setId(id)
                .setValue(value)
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.ROUNDED))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.HTML))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.TREE_MOVING))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.CONNECTABLE))
                .addStyleParam(BooleanStyleParam.createAsFalse(BooleanStyleParam.Key.RESIZABLE))
                .addStyleParam(WhiteSpaceStyleParam.WRAP)
                .setGeometry(ComplexNodeGeometry.create(x, y, width, height))
                .build();

        diagramRoot.getCells().add(nodeCell);
        addEdge(diagramRoot);
    }
}
