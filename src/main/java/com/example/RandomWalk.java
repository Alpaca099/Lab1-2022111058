package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RandomWalk {

    private Graph graph; // 引用图对象
    private Random rand; // 随机数生成器

    public RandomWalk(Graph graph) {
        this.graph = graph;
        this.rand = new Random();
    }

    // 随机游走方法
    public void executeRandomWalk() throws IOException {
        List<String> vertices = new ArrayList<>(graph.getVertices());

        // 随机选择一个起始节点
        String currentNode = vertices.get(rand.nextInt(vertices.size()));
        //String currentNode = "to";
        System.out.println("随机选择的起始节点: " + currentNode);

        Set<String> visitedEdges = new HashSet<>();
        List<String> walkSequence = new ArrayList<>();
        walkSequence.add(currentNode);

        while (true) {
            Map<String, Integer> neighbors = graph.getEdgesFrom(currentNode);
            if (neighbors == null || neighbors.isEmpty()) {
                // 如果当前节点没有出边，停止遍历
                break;
            }

            // 随机选择一条出边
            List<String> neighborList = new ArrayList<>(neighbors.keySet());
            String nextNode = neighborList.get(rand.nextInt(neighborList.size()));

            // 记录经过的边
            String edge = currentNode + "->" + nextNode;
            if (visitedEdges.contains(edge)) {
                // 如果边已经访问过，停止遍历
                break;
            }

            // 记录节点和边
            visitedEdges.add(edge);
            walkSequence.add(nextNode);
            currentNode = nextNode; // 移动到下一个节点
        }

        // 输出遍历结果到文件
        String walkfilepath = "E:/D/CODE/SE/lab/lab1/lab1/src/main/java/com/example/Walk.txt";
        saveWalkSequenceToFile(walkSequence, walkfilepath);
    }

    // 将随机游走结果保存到文件
    private void saveWalkSequenceToFile(List<String> walkSequence, String outputPath) throws IOException {
        try (FileWriter writer = new FileWriter(outputPath)) {
            for (String node : walkSequence) {
                writer.write(node + " ");
            }
        }
    }
}
