package com.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BridgeWordFinder {

    private final Graph graph;

    public BridgeWordFinder(Graph graph) {
        this.graph = graph;
    }

    public void findAndPrintBridgeWords() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入第一个单词 (word1): ");
        String word1 = scanner.nextLine().toLowerCase();  // 注意统一小写

        System.out.print("请输入第二个单词 (word2): ");
        String word2 = scanner.nextLine().toLowerCase();

        // 检查单词是否存在于图中
        List<String> notFoundWords = new ArrayList<>();
        if (!graph.getVertices().contains(word1)) {
            notFoundWords.add(word1);
        }
        if (!graph.getVertices().contains(word2)) {
            notFoundWords.add(word2);
        }

        if (!notFoundWords.isEmpty()) {
            System.out.println("No " + "\"" +String.join(" and ", notFoundWords) +"\"" + " in the graph!");
            return;
        }

        List<String> bridgeWords = graph.findBridgeWords(word1, word2);

        if (bridgeWords == null) {
            System.out.println("No " + "\"" +word1 + "\"" +" or " + "\"" +word2 + "\"" +" in the graph!");
        } else if (bridgeWords.isEmpty()) {
            System.out.println("No bridge words from " + "\"" +word1 + "\"" +" to " + "\"" +word2 + "\"" +"!");
        } else {
            System.out.print("The bridge words from " + "\"" +word1 + "\"" +" to " + "\"" +word2 + "\"" +" are: ");
            for (int i = 0; i < bridgeWords.size(); i++) {
                System.out.print(bridgeWords.get(i));
                if (i < bridgeWords.size() - 2) {
                    System.out.print(", ");
                } else if (i == bridgeWords.size() - 2) {
                    System.out.print(", and ");
                }
            }
            System.out.println(".");
        }
    }
}

