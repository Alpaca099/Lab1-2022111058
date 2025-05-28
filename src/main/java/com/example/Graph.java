package com.example;

import java.util.*;

public class Graph {
    private Map<String, Map<String, Integer>> adjacencyMap = new HashMap<>();

    public List<String> findBridgeWords(String word1, String word2) {
        List<String> bridgeWords = new ArrayList<>();

        // 先判断 word1 和 word2 是否都存在
        if (!adjacencyMap.containsKey(word1)) {
            if (!adjacencyMap.containsKey(word2)) {
                return Collections.singletonList("no word 1 and no word 2");
            }
            return Collections.singletonList("no word 1");  // 表示找不到节点
        }

        if (!adjacencyMap.containsKey(word2)) {
            return Collections.singletonList("no word 2");  // 表示找不到节点
        }

        // 遍历 word1 出发的所有邻居节点
        Map<String, Integer> fromEdges = adjacencyMap.get(word1);
        for (String middle : fromEdges.keySet()) {
            // 检查 middle 是否连接到 word2
            Map<String, Integer> middleEdges = adjacencyMap.get(middle);
            if (middleEdges != null && middleEdges.containsKey(word2)) {
                bridgeWords.add(middle);
            }
        }

        return bridgeWords;
    }

    public void addEdge(String from, String to) {
        // 如果 from 节点不存在，初始化它
        adjacencyMap.putIfAbsent(from, new HashMap<>());
        // 如果 to 节点不存在，初始化它（确保所有节点都至少有一个空的邻接表）
        adjacencyMap.putIfAbsent(to, new HashMap<>());

        // 增加边的权重
        Map<String, Integer> edges = adjacencyMap.get(from);
        edges.put(to, edges.getOrDefault(to, 0) + 1);
    }

    public Set<String> getVertices() {
        return adjacencyMap.keySet();
    }

    public Map<String, Integer> getEdgesFrom(String vertex) {
        return adjacencyMap.getOrDefault(vertex, Collections.emptyMap());
    }

    public void printGraph() {
        for (String from : adjacencyMap.keySet()) {
            Map<String, Integer> edges = adjacencyMap.get(from);
            for (Map.Entry<String, Integer> entry : edges.entrySet()) {
                System.out.println(from + " -> " + entry.getKey() + " (weight: " + entry.getValue() + ")");
            }
        }
    }
}
