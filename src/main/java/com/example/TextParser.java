package com.example;

import java.io.*;
import java.util.*;

public class TextParser {
    public static List<String> parseTextFile(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(" ");
            }
        }
        String sanitized = content.toString().replaceAll("[^a-zA-Z ]", " ").toLowerCase();
        return Arrays.asList(sanitized.trim().split("\\s+"));
    }
}

