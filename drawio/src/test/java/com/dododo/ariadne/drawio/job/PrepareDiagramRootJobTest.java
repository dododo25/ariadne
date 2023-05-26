package com.dododo.ariadne.drawio.job;

import com.dododo.ariadne.drawio.model.Block;
import com.dododo.ariadne.drawio.model.ChainBlock;
import com.dododo.ariadne.drawio.model.EndBlock;
import com.dododo.ariadne.drawio.model.EntryBlock;
import com.dododo.ariadne.drawio.model.StatementBlock;
import com.dododo.ariadne.drawio.model.SwitchBlock;
import com.dododo.ariadne.drawio.mxg.DiagramRoot;
import com.dododo.ariadne.drawio.mxg.MxAbstractCell;
import com.dododo.ariadne.drawio.mxg.MxEdgeCell;
import com.dododo.ariadne.drawio.mxg.MxFile;
import com.dododo.ariadne.drawio.mxg.MxNodeCell;
import com.dododo.ariadne.drawio.mxg.geometry.ComplexNodeGeometry;
import com.dododo.ariadne.drawio.mxg.geometry.EdgeGeometry;
import com.dododo.ariadne.drawio.mxg.geometry.RelativeNodeGeometry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PrepareDiagramRootJobTest {

    private MxFile mxFile;

    private DiagramRoot root;

    @BeforeEach
    void setUp() {
        root = new DiagramRoot();
        mxFile = createMxFile(root);
    }

    @Test
    void testRunWhenRootBlockIsEntryStateShouldDoneWell() {
        BlockToMxCellFactory factory = new BlockToMxCellFactory() {
            @Override
            public ChainBlock createBlock(int id) {
                return new EntryBlock(id);
            }

            @Override
            public MxNodeCell createMxCell(int id) {
                return new MxNodeCell.Builder()
                        .setId(id)
                        .setGeometry(ComplexNodeGeometry.create(0, 0, 40, 40))
                        .build();
            }
        };

        testRunWhenRootBlockIsChainBlock(factory);
    }

    @Test
    void testRunWhenRootBlockIsStatementShouldDoneWell() {
        BlockToMxCellFactory factory = new BlockToMxCellFactory() {
            @Override
            public ChainBlock createBlock(int id) {
                return new StatementBlock(id, "test");
            }

            @Override
            public MxNodeCell createMxCell(int id) {
                return new MxNodeCell.Builder()
                        .setId(id)
                        .setValue("test")
                        .setGeometry(ComplexNodeGeometry.create(0, 0, 120, 60))
                        .build();
            }
        };

        testRunWhenRootBlockIsChainBlock(factory);
    }

    @Test
    void testRunWhenRootBlockIsSwitchShouldDoneWell() {
        MxNodeCell nodeCell1 = new MxNodeCell.Builder()
                .setId(0)
                .setValue("test")
                .setGeometry(ComplexNodeGeometry.create(0, 0, 120, 120))
                .build();

        MxNodeCell nodeCell2 = new MxNodeCell.Builder()
                .setId(1)
                .setGeometry(ComplexNodeGeometry.create(0, 0, 40, 40))
                .build();

        MxNodeCell nodeCell3 = new MxNodeCell.Builder()
                .setId(2)
                .setGeometry(ComplexNodeGeometry.create(0, 0, 40, 40))
                .build();

        MxEdgeCell edgeCell1 = new MxEdgeCell.Builder()
                .setSource(0)
                .setTarget(1)
                .setIdSuffix("t")
                .setExitPoint(0.5, 0)
                .setGeometry(EdgeGeometry.create(0, 0, 0, 120))
                .build();

        MxEdgeCell edgeCell2 = new MxEdgeCell.Builder()
                .setSource(0)
                .setTarget(2)
                .setIdSuffix("f")
                .setExitPoint(0.5, 0)
                .setGeometry(EdgeGeometry.create(0, 0, 160, 120))
                .build();

        MxNodeCell nodeCell4 = new MxNodeCell.ParentCellBuilder()
                .setId(edgeCell1, "tl")
                .setValue("true")
                .setGeometry(RelativeNodeGeometry.create(-1, 0))
                .build();

        MxNodeCell nodeCell5 = new MxNodeCell.ParentCellBuilder()
                .setId(edgeCell2, "fl")
                .setValue("false")
                .setGeometry(RelativeNodeGeometry.create(-1, 0))
                .build();

        SwitchBlock switchBlock = new SwitchBlock(0, "test");
        EndBlock endBlock1 = new EndBlock(1);
        EndBlock endBlock2 = new EndBlock(2);

        switchBlock.setTrueBranch(endBlock1);
        switchBlock.setFalseBranch(endBlock2);

        testRunShouldDoneWell(switchBlock, nodeCell1, edgeCell1, edgeCell2, nodeCell4, nodeCell5, nodeCell2, nodeCell3);
    }

    private void testRunWhenRootBlockIsChainBlock(BlockToMxCellFactory factory) {
        int rootBlockId = 0;
        int childBlockId = 1;

        MxNodeCell nodeCell1 = factory.createMxCell(rootBlockId);

        MxNodeCell nodeCell2 = new MxNodeCell.Builder()
                .setId(childBlockId)
                .setGeometry(ComplexNodeGeometry.create(0, 0, 40, 40))
                .build();

        MxEdgeCell edgeCell = new MxEdgeCell.Builder()
                .setSource(rootBlockId)
                .setTarget(childBlockId)
                .setEntryPoint(0.5, 0)
                .setExitPoint(0.5, 1)
                .build();

        ChainBlock rootBlock = factory.createBlock(rootBlockId);
        EndBlock endBlock = new EndBlock(childBlockId);

        rootBlock.setNext(endBlock);

        testRunShouldDoneWell(rootBlock, nodeCell1, edgeCell, nodeCell2);
    }

    private void testRunShouldDoneWell(Block rootBlock, MxAbstractCell... expected) {
        PrepareDiagramRootJob job = new PrepareDiagramRootJob(new AtomicReference<>(mxFile),
                new AtomicReference<>(rootBlock));

        job.run();

        Assertions.assertIterableEquals(Arrays.asList(expected), root.getCells());
    }

    private MxFile createMxFile(DiagramRoot root) {
        MxFile file = mock(MxFile.class, RETURNS_DEEP_STUBS);

        when(file.getDiagram().getModel().getRoot())
                .thenReturn(root);

        return file;
    }

    private interface BlockToMxCellFactory {

        ChainBlock createBlock(int id);

        MxNodeCell createMxCell(int id);

    }
}
