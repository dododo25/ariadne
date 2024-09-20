package com.dododo.ariadne.drawio.model;

import com.dododo.ariadne.mxg.model.DiagramRoot;
import com.dododo.ariadne.mxg.model.MxEdgeCell;
import com.dododo.ariadne.mxg.style.BooleanStyleParam;

public abstract class ChainBlock extends Block {

    protected Block next;

    protected ChainBlock(int id) {
        super(id);
    }

    public Block getNext() {
        return next;
    }

    public void setNext(Block next) {
        this.next = next;

        if (this.next != null) {
            this.next.addRoot(this);
        }
    }

    protected void addEdge(DiagramRoot diagramRoot) {
        MxEdgeCell.Builder edgeCellBuilder = new MxEdgeCell.Builder()
                .setSource(id)
                .setTarget(next.id)
                .setExitPoint(0.5, 1)
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.HTML))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.ROUNDED))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.ORTHOGONAL_LOOP))
                .addStyleParam(BooleanStyleParam.createAsTrue(BooleanStyleParam.Key.ORTHOGONAL));

        if (!(next instanceof MenuBlock) || next.roots.size() == 1) {
            edgeCellBuilder.setEntryPoint(0.5, 0);
        }

        diagramRoot.getCells().add(edgeCellBuilder.build());
    }
}
