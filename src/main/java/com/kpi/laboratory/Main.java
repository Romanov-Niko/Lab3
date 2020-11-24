package com.kpi.laboratory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = "DS5.txt";
        File file = new File(fileName);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> lines = new ArrayList<String>();
        String line = bufferedReader.readLine();
        while (line != null) {
            line = bufferedReader.readLine();
            lines.add(line);
        }
        ImageMapper imageMapper = new ImageMapper(lines);
        imageMapper.mapImage(960, 540);
    }
}
