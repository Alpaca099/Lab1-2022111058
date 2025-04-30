package com.example;

import java.util.*;

public class PageRank {
    private static final double DAMPING_FACTOR = 0.85;  // 阻尼因子
    private static final int MAX_ITERATIONS = 100;      // 最大迭代次数
    private static final double CONVERGENCE_THRESHOLD = 0.0001;  // 收敛阈值

    private Graph graph;
    private Map<String, Double> pageRankScores;


    public PageRank(Graph graph) {
        this.graph = graph;
        this.pageRankScores = new HashMap<>();
    }

    // 初始化所有节点的 PageRank 值
    public void initializePageRanks() {
        double initialRank = 1.0 / (graph.getVertices().size());  // 初始值均等
        for (String vertex : graph.getVertices()) {
            pageRankScores.put(vertex, initialRank);
        }
    }

    // 计算 PageRank 值
    public void calculatePageRank() {
        initializePageRanks();

        Set<String> nodes = graph.getVertices();  // 获取所有节点
        int N = nodes.size();  // 节点总数
        double danglingSum;

        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            Map<String, Double> newPageRankScores = new HashMap<>();

            // 计算悬挂节点的PR总和
            danglingSum = 0.0;
            for (String vertex : nodes) {
                if (graph.getEdgesFrom(vertex).isEmpty()) {
                    danglingSum += pageRankScores.get(vertex);  // 如果出度为0，计算其PR贡献
                }
            }

            // 计算每个节点的新PR值
            for (String vertex : nodes) {
                double rankSum = 0.0;
                // 遍历所有指向当前节点的节点
                for (String neighbor : nodes) {
                    if (graph.getEdgesFrom(neighbor).containsKey(vertex)) {
                        // 如果neighbor指向vertex，计算出度贡献
                        double neighborRank = pageRankScores.get(neighbor);
                        int outDegree = graph.getEdgesFrom(neighbor).size();
                        if (outDegree == 0) {
                            // 悬挂节点，均分PR值
                            neighborRank = pageRankScores.get(neighbor) / N;
                        } else {
                            neighborRank = pageRankScores.get(neighbor) / outDegree;
                        }
                        rankSum += neighborRank;
                    }
                }

                // 计算新的PR值
                double newRank = (1 - DAMPING_FACTOR) / N + DAMPING_FACTOR * rankSum + DAMPING_FACTOR * danglingSum / N;
                newPageRankScores.put(vertex, newRank);
            }

            // 检查是否收敛
            boolean converged = true;
            for (String vertex : nodes) {
                if (Math.abs(newPageRankScores.get(vertex) - pageRankScores.get(vertex)) > CONVERGENCE_THRESHOLD) {
                    converged = false;
                    break;
                }
            }

            // 如果PR值已经收敛，则停止迭代
            if (converged) {
                break;
            }

            pageRankScores = newPageRankScores;
        }
    }

    // 获取计算出来的 PageRank 值
    public Map<String, Double> getPageRankScores() {
        return pageRankScores;
    }

    // 打印 PR 值
    public void printPageRankScores() {
        for (Map.Entry<String, Double> entry : pageRankScores.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
