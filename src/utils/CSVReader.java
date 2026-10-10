package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CSVReader {

    public static ArrayList<String[]> readCSV(String filePath) {
        ArrayList<String[]> rows = new ArrayList<String[]>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                rows.add(line.split(","));
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("[!] Error reading file: " + filePath);
            System.out.println("    Reason: " + e.getMessage());
            return null;
        }
        return rows;
    }
}