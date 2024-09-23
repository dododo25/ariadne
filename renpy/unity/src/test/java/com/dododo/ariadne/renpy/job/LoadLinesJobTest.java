package com.dododo.ariadne.renpy.job;

import com.dododo.ariadne.core.configuration.Configuration;
import com.dododo.ariadne.test.resolver.FlowchartTypeResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(FlowchartTypeResolver.class)
class LoadLinesJobTest {

    @TempDir
    private Path tempDir;

    @Test
    void testRun() throws IOException {
        List<String> expected = Arrays.asList(
                "label test1                   ",
                "    $ a = 0                   ",
                "label test2                   ",
                "    menu {                    ",
                "        text0001: 'option1' { ",
                "            $ a = 1           ",
                "        }                     ",
                "        text0002: 'option2' { ",
                "            $ a = 2           ",
                "        }                     ",
                "    }                         "
        );

        Path tempFile1 = Files.createFile(tempDir.resolve("temp_file#1.txt"));
        Path tempFile2 = Files.createFile(tempDir.resolve("temp_file#2.txt"));

        Files.write(tempFile1, Arrays.asList(
                "label test",
                "    $ a = 0"));

        Files.write(tempFile2, Arrays.asList(
                "label test2",
                "    menu {",
                "        text0001: 'option1' {",
                "            $ a = 1",
                "        }",
                "        text0002: 'option2' {",
                "            $ a = 2",
                "        }",
                "    }"));

        Configuration config = createConfig(tempFile1, tempFile2);
        List<String> lines = new ArrayList<>();

        for (int i = 0; i < config.getInputFiles().size(); i++) {
            LoadLinesJob job = new LoadLinesJob(i, lines);

            job.setConfiguration(config);
            job.run();
        }

        Assertions.assertEquals(expected.size(), lines.size());
        IntStream.range(0, lines.size())
                .forEach(index -> Assertions.assertTrue(expected.get(index).startsWith(lines.get(index))));
    }

    private Configuration createConfig(Path path1, Path path2) {
        Configuration configuration = mock(Configuration.class);

        when(configuration.getInputFiles())
                .thenReturn(Arrays.asList(path1.toFile().getPath(), path2.toFile().getPath()));

        return configuration;
    }
}
