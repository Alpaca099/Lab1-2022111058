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



    // 使用 TF-IDF 初始化所有节点的 PageRank 值
    public void initializePageRanksWithTFIDF(List<String> textWords) {
        Map<String, Integer> tfMap = new HashMap<>();
        int totalWords = textWords.size();

        // 计算 TF
        for (String word : textWords) {
            tfMap.put(word, tfMap.getOrDefault(word, 0) + 1);
        }

        // 计算 TF-IDF（简单估算的 IDF）
        Map<String, Double> tfidfMap = new HashMap<>();
        for (String word : tfMap.keySet()) {
            double tf = (double) tfMap.get(word) / totalWords;
            double idf = Math.log(1 + (double) totalWords / tfMap.get(word));  // 近似 IDF
            tfidfMap.put(word, tf * idf);
        }

        // 归一化 TF-IDF 得分作为初始 PR 值
        double sumTFIDF = tfidfMap.values().stream().mapToDouble(Double::doubleValue).sum();
        for (String vertex : graph.getVertices()) {
            double score = tfidfMap.getOrDefault(vertex, 0.0);
            pageRankScores.put(vertex, sumTFIDF == 0 ? 1.0 / graph.getVertices().size() : score / sumTFIDF);
        }
    }

    // 计算 PageRank 值（使用 TF-IDF 初始化）
    public void NewcalculatePageRank(List<String> textWords) {
        initializePageRanksWithTFIDF(textWords);

        Set<String> nodes = graph.getVertices();
        int N = nodes.size();
        double danglingSum;

        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            Map<String, Double> newPageRankScores = new HashMap<>();

            // 计算悬挂节点的 PR 值之和
            danglingSum = 0.0;
            for (String vertex : nodes) {
                if (graph.getEdgesFrom(vertex).isEmpty()) {
                    danglingSum += pageRankScores.get(vertex);
                }
            }

            // 更新每个节点的 PR 值
            for (String vertex : nodes) {
                double rankSum = 0.0;
                for (String neighbor : nodes) {
                    if (graph.getEdgesFrom(neighbor).containsKey(vertex)) {
                        double neighborRank = pageRankScores.get(neighbor);
                        int outDegree = graph.getEdgesFrom(neighbor).size();
                        neighborRank = (outDegree == 0) ? neighborRank / N : neighborRank / outDegree;
                        rankSum += neighborRank;
                    }
                }

                double newRank = (1 - DAMPING_FACTOR) / N + DAMPING_FACTOR * rankSum + DAMPING_FACTOR * danglingSum / N;
                newPageRankScores.put(vertex, newRank);
            }

            // 判断是否收敛
            boolean converged = true;
            for (String vertex : nodes) {
                if (Math.abs(newPageRankScores.get(vertex) - pageRankScores.get(vertex)) > CONVERGENCE_THRESHOLD) {
                    converged = false;
                    break;
                }
            }

            pageRankScores = newPageRankScores;

            if (converged) break;
        }
    }

}
