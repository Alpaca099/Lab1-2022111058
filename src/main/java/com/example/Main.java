package com.example;
//R7:B2 change_1
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
////////////////////////////////////////////////////////////////////////
//展示有向图
            //System.out.println("\n有向图结构如下：");
            //graph.printGraph();
///////////////////////////////////////////////////////////////////////
//画图
            // 绘制图保存
            GraphDraw.drawGraph(graph);

//////////////////////////////////////////////////////////////////////
//桥接词查询
//            BridgeWordFinder bridgeWordFinder = new BridgeWordFinder(graph);
//            bridgeWordFinder.findAndPrintBridgeWords();

/// //////////////////////////////////////////////////////////////////
//根据bridge word生成新文本
//            //创建 BridgeWordInserter 对象
//            BridgeWordInserter inserter = new BridgeWordInserter(graph);
//            String resultText = inserter.processTextFromStdIn();
//
//            // 输出生成的新文本到命令行
//            System.out.println("生成的新文本：");
//            System.out.println(resultText);


///////////////////////////////////////////////////////////////////////
//最短路径搜索
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
            RandomWalk randomWalk = new RandomWalk(graph);
            randomWalk.executeRandomWalk();


        }catch (java.io.IOException e) {
                // 处理文件读取的异常
                System.err.println("文件读取错误：" + e.getMessage());
                e.printStackTrace();
            } catch (NullPointerException e) {
                // 处理可能的空指针异常
                System.err.println("出现空指针异常：" + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                // 捕获其他通用异常
                System.err.println("发生未知错误：" + e.getMessage());
                e.printStackTrace();
            }
    }
}
