package org.sbpstu.buglaevsa;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import org.junit.jupiter.api.Test;
import static org.apache.commons.io.FileUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class ConsoleTest {

    private File getFileResources(String filename) throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource(filename);
        return new File(url.toURI());
    }

    private String MySystemOut(String[] args) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;
        System.setOut(new PrintStream(baos));
        System.setErr(new PrintStream(baos));

        CutLauncher.main(args);

        System.out.flush();
        System.err.flush();
        System.setOut(oldOut);
        System.setErr(oldErr);
        return (baos.toString());
    }

    @Test
    void SystemOutTest() throws URISyntaxException {
        File inputFile = getFileResources("input/input2.txt");
        assertEquals("ashisa to w", MySystemOut(("-c -r 4-14 " + inputFile).split(" ")));
    }

    @Test
    void CutTest() throws IOException, URISyntaxException {

        File inputFile = getFileResources("input/input1.txt");
        File outputFile = getFileResources("output/output1.txt");
        File expected = getFileResources("expected/expected1.txt");
        Cut cut = new Cut(true, "4-");
        cut.cutter(inputFile, outputFile);
        assertTrue(contentEquals(outputFile, expected));

        File outputFile2 = getFileResources("output/output2.txt");
        File expected2 = getFileResources("expected/expected2.txt");
        Cut cut2 = new Cut(true, "4-14");
        cut2.cutter(inputFile, outputFile2);
        assertTrue(contentEquals(outputFile2, expected2));

        File outputFile3 = getFileResources("output/output3.txt");
        File expected3 = getFileResources("expected/expected3.txt");
        Cut cut3 = new Cut(true, "-14");
        cut3.cutter(inputFile, outputFile3);
        assertTrue(contentEquals(outputFile3, expected3));

        File outputFile4 = getFileResources("output/output4.txt");
        Cut cut4 = new Cut(true, "1-1000");
        cut4.cutter(inputFile, outputFile4);
        assertTrue(contentEquals(outputFile4, inputFile));

        File outputFile5 = getFileResources("output/output5.txt");
        File expected5 = getFileResources("expected/expected5.txt");
        Cut cut5 = new Cut(false, "4-");
        cut5.cutter(inputFile, outputFile5);
        assertTrue(contentEquals(outputFile5, expected5));

        File outputFile6 = getFileResources("output/output6.txt");
        File expected6 = getFileResources("expected/expected6.txt");
        Cut cut6 = new Cut(false, "2-5");
        cut6.cutter(inputFile, outputFile6);
        assertTrue(contentEquals(outputFile6, expected6));

        File outputFile7 = getFileResources("output/output7.txt");
        File expected7 = getFileResources("expected/expected7.txt");
        Cut cut7 = new Cut(false, "-4");
        cut7.cutter(inputFile, outputFile7);
        assertTrue(contentEquals(outputFile7, expected7));

        File outputFile8 = getFileResources("output/output8.txt");
        Cut cut8 = new Cut(false, "1-1000");
        cut8.cutter(inputFile, outputFile8);
        assertTrue(contentEquals(outputFile8, inputFile));

    }

    @Test
    void CutLauncherTest() throws IOException, URISyntaxException {

        File inputFile = getFileResources("input/input1.txt");
        File outputFile = getFileResources("output/output1.txt");
        File expected = getFileResources("expected/expected1.txt");
        CutLauncher.main(("-c -o " + outputFile + " -r 4- " + inputFile).split(" "));
        assertTrue(contentEquals(outputFile, expected));

        File outputFile2 = getFileResources("output/output2.txt");
        File expected2 = getFileResources("expected/expected2.txt");
        CutLauncher.main(("-c -o " + outputFile2 + " -r 4-14 " + inputFile).split(" "));
        assertTrue(contentEquals(outputFile2, expected2));

        File outputFile3 = getFileResources("output/output3.txt");
        File expected3 = getFileResources("expected/expected3.txt");
        CutLauncher.main(("-c -o " + outputFile3 + " -r -14 " + inputFile).split(" "));
        assertTrue(contentEquals(outputFile3, expected3));

        File outputFile4 = getFileResources("output/output4.txt");
        CutLauncher.main(("-c -o " + outputFile4 + " -r 1-1000 " + inputFile).split(" "));
        assertTrue(contentEquals(outputFile4, inputFile));

        File outputFile5 = getFileResources("output/output5.txt");
        File expected5 = getFileResources("expected/expected5.txt");
        CutLauncher.main(("-w -o " + outputFile5 + " -r 4- " + inputFile).split(" "));
        assertTrue(contentEquals(outputFile5, expected5));

        File outputFile6 = getFileResources("output/output6.txt");
        File expected6 = getFileResources("expected/expected6.txt");
        CutLauncher.main(("-w -o " + outputFile6 + " -r 2-5 " + inputFile).split(" "));
        assertTrue(contentEquals(outputFile6, expected6));

        File outputFile7 = getFileResources("output/output7.txt");
        File expected7 = getFileResources("expected/expected7.txt");
        CutLauncher.main(("-w -o " + outputFile7 + " -r -4 " + inputFile).split(" "));
        assertTrue(contentEquals(outputFile7, expected7));

        File outputFile8 = getFileResources("output/output8.txt");
        CutLauncher.main(("-w -o " + outputFile8 + " -r 1-1000 " + inputFile).split(" "));
        assertTrue(contentEquals(outputFile8, inputFile));


    }

}