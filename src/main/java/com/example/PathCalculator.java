package com.example;

import java.util.*;

public class PathCalculator {
    private Graph graph;

    public PathCalculator(Graph graph) {
        this.graph = graph;
    }

    // 基于 Dijkstra 的所有最短路径查找（适用于权重不同的图）
    public List<List<String>> findAllShortestPaths(String start, String end) {
        Map<String, Integer> dist = new HashMap<>();
        Map<String, List<String>> prevs = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get));

        // 初始化
        for (String vertex : graph.getVertices()) {
            dist.put(vertex, Integer.MAX_VALUE);
            prevs.put(vertex, new ArrayList<>());
        }
        dist.put(start, 0);
        pq.offer(start);

        // Dijkstra 主体
        while (!pq.isEmpty()) {
            String current = pq.poll();
            int currentDist = dist.get(current);

            for (Map.Entry<String, Integer> neighbor : graph.getEdgesFrom(current).entrySet()) {
                String next = neighbor.getKey();
                int weight = neighbor.getValue();
                int newDist = currentDist + weight;

                if (newDist < dist.get(next)) {
                    dist.put(next, newDist);
                    prevs.get(next).clear();
                    prevs.get(next).add(current);
                    pq.offer(next);
                } else if (newDist == dist.get(next)) {
                    // 如果有另一条同样长度的路径，也加入前驱节点
                    prevs.get(next).add(current);
                }
            }
        }

        // 回溯所有路径
        List<List<String>> allPaths = new ArrayList<>();
        LinkedList<String> path = new LinkedList<>();
        backtrack(end, start, prevs, path, allPaths);
        return allPaths;
    }

    // 回溯路径构造所有路径
    private void backtrack(String current, String start,
                           Map<String, List<String>> prevs,
                           LinkedList<String> path,
                           List<List<String>> allPaths) {
        path.addFirst(current);
        if (current.equals(start)) {
            allPaths.add(new ArrayList<>(path));
        } else {
            for (String prev : prevs.get(current)) {
                backtrack(prev, start, prevs, path, allPaths);
            }
        }
        path.removeFirst();
    }

    // 展示路径
    public void displayShortestPath(String word1, String word2) {
        if (word2 == null || word2.isEmpty()) {
            System.out.println("展示从 \"" + word1 + "\" 到其他单词的最短路径：");
            for (String vertex : graph.getVertices()) {
                if (!vertex.equals(word1)) {
                    List<List<String>> paths = findAllShortestPaths(word1, vertex);
                    if (paths.isEmpty()) {
                        System.out.println("No path found from \"" + word1 + "\" to \"" + vertex + "\".");
                    } else {
                        System.out.println("\nThe shortest paths from \"" + word1 + "\" to \"" + vertex + "\" are:");
                        for (List<String> path : paths) {
                            System.out.println(String.join(" -> ", path));
                        }
                    }
                }
            }
        } else {
            List<List<String>> paths = findAllShortestPaths(word1, word2);
            if (paths.isEmpty()) {
                System.out.println("No path found between \"" + word1 + "\" and \"" + word2 + "\".");
            } else {
                System.out.println("The shortest paths from \"" + word1 + "\" to \"" + word2 + "\" are:");
                for (List<String> path : paths) {
                    System.out.println(String.join(" -> ", path));
                }
            }
        }
    }
}
