package com.example;


import java.util.*;

public class PathCalculator {
    private Graph graph;

    // 构造函数，接收图的对象
    public PathCalculator(Graph graph) {
        this.graph = graph;
    }

    // 查找两个单词之间的最短路径
    public List<String> findShortestPath(String start, String end) {
        // 初始化距离和前驱节点
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(dist::get));

        // 初始化所有节点
        for (String vertex : graph.getVertices()) {
            dist.put(vertex, Integer.MAX_VALUE);  // 初始距离设为最大值
            prev.put(vertex, null);  // 没有前驱节点
        }
        dist.put(start, 0);  // 起点到起点的距离是 0
        queue.add(start);

        // 使用 Dijkstra 算法进行最短路径计算
        while (!queue.isEmpty()) {
            String current = queue.poll();

            // 如果当前节点已经是目标节点，结束计算
            if (current.equals(end)) {
                break;
            }

            // 遍历当前节点的邻居节点
            Map<String, Integer> neighbors = graph.getEdgesFrom(current);
            for (Map.Entry<String, Integer> neighbor : neighbors.entrySet()) {
                String neighborVertex = neighbor.getKey();
                int newDist = dist.get(current) + neighbor.getValue();

                if (newDist < dist.get(neighborVertex)) {
                    dist.put(neighborVertex, newDist);
                    prev.put(neighborVertex, current);
                    queue.add(neighborVertex);
                }
            }
        }

        // 如果找不到路径，返回空列表
        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = prev.get(at)) {
            path.add(at);
        }

        Collections.reverse(path);  // 路径从终点到起点，反转路径
        return path.size() == 1 ? new ArrayList<>() : path;  // 如果路径只有终点，说明没找到路径
    }

    // 展示路径
    public void displayShortestPath(String word1, String word2) {
        if (word2 == null || word2.isEmpty()) {
            // 如果第二个单词为空，计算第一个单词到图中其他所有单词的最短路径
            System.out.println("展示从 " + "\"" +word1 + "\"" +" 到其他单词的最短路径：");
            for (String vertex : graph.getVertices()) {
                if (!vertex.equals(word1)) {
                    List<String> path = findShortestPath(word1, vertex);
                    if (path.isEmpty()) {
                        System.out.println("No path found from " + "\"" +word1 + "\"" +" to " + "\"" +vertex +"\"" + ".");
                    } else {
                        System.out.println("The shortest path from " + "\"" +word1 + "\"" +" to " + "\"" +vertex + "\"" +" is: ");
                        System.out.println(String.join(" -> ", path));
                    }
                }
            }
        } else {
            // 否则计算从 word1 到 word2 的最短路径
            List<String> path = findShortestPath(word1, word2);

            if (path.isEmpty()) {
                System.out.println("No path found between " + "\"" +word1 + "\"" +" and " + "\"" +word2 +"\"" + ".");
            } else {
                System.out.println("The shortest path from " + "\"" +word1 +"\"" + " to " +"\"" + word2 + "\"" +" is: ");
                System.out.println(String.join(" -> ", path));
            }
        }
    }

}

