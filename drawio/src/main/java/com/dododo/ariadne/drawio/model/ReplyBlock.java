package com.dododo.ariadne.drawio.model;

import com.dododo.ariadne.drawio.contract.BlockFlowchartContract;
import com.dododo.ariadne.drawio.factory.BlockComparatorFactory;
import com.dododo.ariadne.drawio.factory.ReplyBlockComparatorFactory;
import com.dododo.ariadne.mxg.model.DiagramRoot;
import com.dododo.ariadne.mxg.model.MxNodeCell;
import com.dododo.ariadne.mxg.geometry.ComplexNodeGeometry;
import com.dododo.ariadne.mxg.style.BooleanStyleParam;
import com.dododo.ariadne.mxg.style.WhiteSpaceStyleParam;

public final class ReplyBlock extends ChainBlock {

    private final String character;

    private final String line;

    public ReplyBlock(int id, String character, String line) {
        super(id);
        this.character = character;
        this.line = line;
    }

    public String getCharacter() {
        return character;
    }

    public String getLine() {
        return line;
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
        return new ReplyBlockComparatorFactory(this);
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
                .setValue(character == null
                        ? String.format("<i>%s</i>", line)
                        : String.format("<p>%s</p><p><i>%s</i></p>", character, line))
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
        if (character == null) {
            return String.format("%s(id=%d, x=%d, y=%d, line='%s')",
                    getClass().getSimpleName(), id, x, y, line);
        } else {
            return String.format("%s(id=%d, x=%d, y=%d, line='%s', character='%s')",
                    getClass().getSimpleName(), id, x, y, line, character);
        }
    }
}
