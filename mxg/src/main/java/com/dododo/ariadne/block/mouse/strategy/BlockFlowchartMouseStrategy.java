package com.dododo.ariadne.block.mouse.strategy;

import com.dododo.ariadne.block.contract.BlockFlowchartContract;
import com.dododo.ariadne.block.model.Block;
import com.dododo.ariadne.block.model.ChainBlock;
import com.dododo.ariadne.block.model.MenuBlock;
import com.dododo.ariadne.block.model.SwitchBlock;
import com.dododo.ariadne.block.mouse.BlockFlowchartMouse;

import java.util.Set;

public interface BlockFlowchartMouseStrategy {

    void acceptChainBlock(ChainBlock block, BlockFlowchartMouse mouse, BlockFlowchartContract callback, Set<Block> visited);

    void acceptMenuBlock(MenuBlock block, BlockFlowchartMouse mouse, BlockFlowchartContract callback, Set<Block> visited);

    void acceptSwitchBlock(SwitchBlock block, BlockFlowchartMouse mouse, BlockFlowchartContract callback, Set<Block> visited);
}
