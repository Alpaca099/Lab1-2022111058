package com.example;

import static guru.nidi.graphviz.model.Factory.*;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class GraphDraw {
    public static void drawGraph(Graph graph) {
        try {
            MutableGraph g = mutGraph("example").setDirected(true);

            for (String from : graph.getVertices()) {
                for (Map.Entry<String, Integer> entry : graph.getEdgesFrom(from).entrySet()) {
                    String to = entry.getKey();
                    int weight = entry.getValue();

                    // 正确方式：from -> to，边上标注权重
                    g.add(
                            mutNode(from).addLink(
                                    to(mutNode(to)).with(Label.of(String.valueOf(weight)))
                            )
                    );
                }
            }

            Graphviz.fromGraph(g).width(800).render(Format.PNG).toFile(new File("graph.png"));
            System.out.println("图已绘制完成，保存为 graph.png");

        } catch (IOException e) {
            System.err.println("绘制图时出错：" + e.getMessage());
            e.printStackTrace();
        }
    }
}

