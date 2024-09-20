package com.dododo.ariadne.drawio.model;

import com.dododo.ariadne.drawio.factory.BlockComparatorFactory;
import com.dododo.ariadne.drawio.contract.BlockFlowchartContract;
import com.dododo.ariadne.mxg.model.DiagramRoot;

import java.util.HashSet;
import java.util.Set;

public abstract class Block {

    protected final Integer id;

    protected final Set<Block> roots;

    protected int x;

    protected int y;

    protected Block(int id) {
        this.id = id;
        this.roots = new HashSet<>();
    }

    public Block[] getRoots() {
        return roots.toArray(new Block[0]);
    }

    public void addRoot(Block block) {
        roots.add(block);
    }

    public int getId() {
        return id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("%s(id=%d, x=%d, y=%d)", getClass().getSimpleName(), id, x, y);
    }

    public abstract void accept(BlockFlowchartContract contract);

    public abstract void accept(DiagramRoot diagramRoot);

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract BlockComparatorFactory getFactory();
}
