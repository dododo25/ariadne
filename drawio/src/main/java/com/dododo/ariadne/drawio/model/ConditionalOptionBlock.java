package com.dododo.ariadne.drawio.model;

import com.dododo.ariadne.drawio.contract.BlockFlowchartContract;
import com.dododo.ariadne.drawio.factory.BlockComparatorFactory;
import com.dododo.ariadne.drawio.factory.ConditionalOptionBlockComparatorFactory;
import com.dododo.ariadne.mxg.model.DiagramRoot;
import com.dododo.ariadne.mxg.model.MxNodeCell;
import com.dododo.ariadne.mxg.geometry.ComplexNodeGeometry;
import com.dododo.ariadne.mxg.style.BooleanStyleParam;
import com.dododo.ariadne.mxg.style.WhiteSpaceStyleParam;

public final class ConditionalOptionBlock extends OptionBlock {

    private final String condition;

    public ConditionalOptionBlock(int id, String value, String condition) {
        super(id, value);
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public BlockComparatorFactory getFactory() {
        return new ConditionalOptionBlockComparatorFactory(this);
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
                .setValue(String.format("<p><i>%s</i></p><p>if %s</p>", value, condition))
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

    @Override
    public String toString() {
        return String.format("%s(id=%d, x=%d, y=%d, value='%s', condition='%s')",
                getClass().getSimpleName(), id, x, y, value, condition);
    }
}
