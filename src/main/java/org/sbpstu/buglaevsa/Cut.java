package org.sbpstu.buglaevsa;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Cut {
    static final String STOP_LINE = "CutEnd";
    private final boolean wayToCut;
    private int start = 1;
    private int end = -1;

    public Cut(boolean wayToCut, String range) {
        this.wayToCut = wayToCut;
        rangeParse(range);
    }

    public void rangeParse(String range) {
        String[] rangeArgs = range.split("-");
        if (range.matches("[0-9]+-[0-9]+")) {
            this.start = Integer.parseInt(rangeArgs[0]);
            this.end = Integer.parseInt(rangeArgs[1]);
            if (start > end) throw new IllegalArgumentException("Wrong range format");
        } else {
            if (range.startsWith("-")) {
                this.end = Integer.parseInt(rangeArgs[1]);
            } else {
                this.start = Integer.parseInt(rangeArgs[0]);
            }
        }
        if (end == 0 || start == 0) throw new IllegalArgumentException("Wrong range format");
    }

    public void cutter(File inputName, File outputName) throws IOException {
        ArrayList<String> listOfString = new ArrayList<>();
        ArrayList<String> cutListOfString = new ArrayList<>();
        if (inputName == null) {
            Scanner in = new Scanner(System.in);
            System.out.println("Write string to cut or write \"CutEnd\" to finish");
            String string = in.nextLine();
            while (in.hasNext() && !string.equals(STOP_LINE)) {
                listOfString.add(string);
                string = in.nextLine();
                if(string.equals("CutEnd")) break;
            }
        } else {
            try (BufferedReader bufReader =
                         new BufferedReader(new FileReader(inputName))) {
                String string = bufReader.readLine();
                while (string != null) {
                    listOfString.add(string);
                    string = bufReader.readLine();
                }
            }
        }
        if (wayToCut) cutListOfString.addAll(charCut(listOfString));
        else cutListOfString.addAll(wordCut(listOfString));

        output(outputName,cutListOfString);
    }

    public void output(File outputName, List<String> cutList) throws IOException {
        if (outputName == null) {
            for (int i = 0; i < cutList.size(); i++) {
                System.out.print(cutList.get(i));
                if (i != cutList.size() - 1) System.out.println();
            }
        } else {
            try (BufferedWriter bw =
                         new BufferedWriter(new FileWriter(outputName))) {
                for (int i = 0; i < cutList.size(); i++) {
                    bw.write(cutList.get(i));
                    if (i != cutList.size() - 1) bw.newLine();
                }
            }
        }
    }

    public List<String> charCut(List<String> listOfString) {
        ArrayList<String> cutListOfString = new ArrayList<>();
        for (String string : listOfString) {
            if (string.length() < start) {
                cutListOfString.add("");
                continue;
            }
            if (string.length() < end) cutListOfString.add(string.substring(start - 1));
            else {
                String cutString = end == -1 ? string.substring(start - 1) : string.substring(start - 1, end);
                cutListOfString.add(cutString);}
        }
        return cutListOfString;
    }

    public List<String> wordCut(List<String> listOfString) {
        ArrayList<String> cutListOfString = new ArrayList<>();
        for (String string : listOfString) {
            List<String> words = Arrays.stream(string.replaceAll("(\\s)+", " ")
                    .split(" ")).collect(Collectors.toList());
            if (words.size() < start) {
                cutListOfString.add("");
                continue;
            }
            if (words.size() < end) cutListOfString.add(String.join(" ", words.subList(start - 1, words.size())));
            else {
                String cutString = end == -1 ?
                        String.join(" ", words.subList(start - 1, words.size()))
                        : String.join(" ", words.subList(start - 1, end));
                cutListOfString.add(cutString);
            }
        }
        return cutListOfString;
    }
}