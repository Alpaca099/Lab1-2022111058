package com.example;

import java.io.IOException;
import java.util.*;

public class BridgeWordInserter {

    private Graph graph;

    public BridgeWordInserter(Graph graph) {
        this.graph = graph;
    }

    // 新增方法，用于从文件读取文本并插入桥接词
    public String processTextFileWithBridgeWords(String filepath) throws IOException {
        // 直接调用 TextParser 来读取文本并得到单词列表
        List<String> words = TextParser.parseTextFile(filepath);

        // 然后调用插入桥接词的功能
        return insertBridgeWords(words);
    }

    // 插入桥接词的核心方法
    public String insertBridgeWords(List<String> words) {
        List<String> resultWords = new ArrayList<>();

        // 遍历每对相邻单词
        for (int i = 0; i < words.size() - 1; i++) {
            String word1 = words.get(i);
            String word2 = words.get(i + 1);

            // 查找这两个单词的桥接词
            List<String> bridgeWords = graph.findBridgeWords(word1, word2);

            if (bridgeWords != null && !bridgeWords.isEmpty()) {
                // 如果找到了桥接词，随机选择一个插入
                Random random = new Random();
                String bridgeWord = bridgeWords.get(random.nextInt(bridgeWords.size()));
                resultWords.add(word1);  // 加入第一个单词
                resultWords.add(bridgeWord);  // 插入桥接词
            } else {
                // 如果没有找到桥接词，直接加入原单词
                resultWords.add(word1);
            }
        }

        // 最后一个单词加入结果
        //resultWords.add(words.get(words.size() - 1));

        // 输出生成的新文本
        return String.join(" ", resultWords);
    }
}
