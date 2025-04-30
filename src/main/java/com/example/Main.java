package com.example;

import java.util.List;
import java.util.Scanner;
//some different :)
public class Main {
    public static void main(String[] args) {
        try {
            //Scanner scanner = new Scanner(System.in);
            //System.out.print("请输入文本文件路径：");
            String filepath = "E:/D/CODE/SE/lab/lab1/lab1/src/main/java/com/example/EasyTest.txt";
            //String filepath = "E:/D/CODE/SE/lab/lab1/lab1/src/main/java/com/example/Long.txt";
            List<String> words = TextParser.parseTextFile(filepath);
            Graph graph = new Graph();

            for (int i = 0; i < words.size() - 1; i++) {
                graph.addEdge(words.get(i), words.get(i + 1));
            }

            System.out.println("\n有向图结构如下：");
            graph.printGraph();
            // 绘制图保存
            // GraphDraw.drawGraph(graph);

//////////////////////////////////////////////////////////////////////
            //桥接词查询
//            BridgeWordFinder bridgeWordFinder = new BridgeWordFinder(graph);
//            bridgeWordFinder.findAndPrintBridgeWords();

/// //////////////////////////////////////////////////////////////////
            // 创建 BridgeWordInserter 对象
            //BridgeWordInserter inserter = new BridgeWordInserter(graph);
            // 读取文本文件并处理
            //String newfilepath = "E:/D/CODE/SE/lab/lab1/lab1/src/main/java/com/example/NewText.txt";
            //String resultText = inserter.processTextFileWithBridgeWords(newfilepath);

            // 输出生成的新文本
            //System.out.println("生成的新文本：");
            //System.out.println(resultText);
            // 创建 PathCalculator 对象

///////////////////////////////////////////////////////////////////////
//            PathCalculator pathCalculator = new PathCalculator(graph);
//            // 用户输入查询两个单词之间的最短路径
//            Scanner scanner = new Scanner(System.in);
//            System.out.print("请输入第一个单词 (word1): ");
//            String word1 = scanner.nextLine().toLowerCase();
//            System.out.print("请输入第二个单词 (word2): ");
//            String word2 = scanner.nextLine().toLowerCase();
//            // 查找并展示最短路径
//            pathCalculator.displayShortestPath(word1, word2);

///////////////////////////////////////////////////////////////////////
             //计算 PageRank
            PageRank pageRank = new PageRank(graph);
            pageRank.calculatePageRank();
            // 打印 PageRank 结果
            pageRank.printPageRankScores();

////////////////////////////////////////////////////////////////////////
            // 创建随机游走对象并执行
//            RandomWalk randomWalk = new RandomWalk(graph);
//            randomWalk.executeRandomWalk();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
