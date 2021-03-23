package org.sbpstu.buglaevsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.*;

public class Cut {
    private boolean wayToCut;
    private int start = 0;
    private int end = -1;

    public Cut(boolean wayToCut, String range) {
        this.wayToCut = wayToCut;
        rangeParse(range);
    }

    public void rangeParse(String range) {
        if (range.matches("[0-9]+-[0-9]+")) {
            String[] ranges = range.split("-");
            this.start = Integer.parseInt(ranges[0]);
            this.end = Integer.parseInt(ranges[1]);
        } else {
            if (range.startsWith("-")) {
                this.end = Integer.parseInt(range) * -1;
            } else {
                this.start = Integer.parseInt(range.substring(0, range.length() - 1));
            }
        }
    }

    public void cutter(File inputName, File outputName) throws IOException {
        ArrayList<String> listOfString = new ArrayList<>();
        ArrayList<String> cutListOfString = new ArrayList<>();
        if (inputName == null) {
            Scanner in = new Scanner(System.in);
            System.out.println("Write string to cut");
            listOfString.add(in.nextLine());
        } else {
            try (BufferedReader bufReader =
                         new BufferedReader(new InputStreamReader(new FileInputStream(inputName)))) {
                String string = bufReader.readLine();
                while (string != null) {
                    listOfString.add(string);
                    string = bufReader.readLine();
                }
            }
        }
        if (wayToCut) cutListOfString.addAll(charCut(listOfString));
        else cutListOfString.addAll(wordCut(listOfString));

        if (outputName == null) {
            cutListOfString.forEach(System.out::println);
        } else {
            try (BufferedWriter bufWriter =
                         new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputName)))) {
                for (String string : cutListOfString) {
                    bufWriter.write(string);
                }
            }
        }
    }

    public List<String> charCut(ArrayList<String> listOfString) {
        ArrayList<String> cutListOfString = new ArrayList<>();
        for (String string : listOfString) {
            String cutString = end == -1 ? string.substring(start - 1) : string.substring(start - 1, end);
            cutListOfString.add(cutString);
        }
        return cutListOfString;
    }

    public List<String> wordCut(ArrayList<String> listOfString) {
        ArrayList<String> cutListOfString = new ArrayList<>();
        for (String string : listOfString) {
            List<String> words = Arrays.stream(string.replaceAll("(\\s)+", " ")
                    .split(" ")).collect(Collectors.toList());
            String cutString = end == -1 ?
                    String.join(" ", words.subList(start, words.size() - 1))
                    : String.join(" ", words.subList(start - 1, end));
            cutListOfString.add(cutString);
        }
        return cutListOfString;
    }

}

