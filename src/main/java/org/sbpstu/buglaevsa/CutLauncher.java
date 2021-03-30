package org.sbpstu.buglaevsa;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineException;

import java.io.File;
import java.io.IOException;
public class CutLauncher {
    @Option(name = "-c", usage = "indents given in chars", forbids = {"-w"})
    private boolean charCut ;

    @Option(name = "-w", usage = "indents given in words", forbids = {"-c"})
    private boolean wordCut  ;

    @Option(name = "-o", usage = "Output file name", metaVar = "OutputFile")
    private File outputFile;

    @Option(name = "-r", usage = "range of cut: N-K ,from N to K", metaVar = "range", required = true)
    private String range;

    @Argument( usage = "Input file name", metaVar = "InputFile")
    private File inputFile;


    public static void main(String[] args) {
        new CutLauncher().parse(args);
    }

    private void parse(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
            if (!charCut && !wordCut || range.length() == 1 || !range.matches("[0-9]*-[0-9]*"))
                throw new CmdLineException(parser,"The way how to cut is not get", new IllegalArgumentException());
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar cut.jar [-c|-w] [-o outputFile]  -r range [InputFile]");
            parser.printUsage(System.err);
            return;
        }
        Cut cut = new Cut(charCut, range);
        try {
            cut.cutter(inputFile, outputFile);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
