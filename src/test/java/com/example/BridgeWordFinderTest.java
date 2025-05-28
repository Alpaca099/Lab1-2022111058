package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BridgeWordFinderTest {

    private Graph graph;

    @BeforeEach
    public void setup() {
        graph = new Graph();
        // 构建示例图（与输入样例一致）
        graph.addEdge("to", "explore");
        graph.addEdge("to", "seek");
        graph.addEdge("to", "find");
        graph.addEdge("explore", "strange");
        graph.addEdge("strange", "new");
        graph.addEdge("seek", "out");
        graph.addEdge("find", "out");
        graph.addEdge("out", "new");
        graph.addEdge("new", "worlds");
        graph.addEdge("new", "life");
        graph.addEdge("new", "civilizations");
        graph.addEdge("worlds", "to");
        graph.addEdge("life", "and");
        graph.addEdge("and", "new");
        graph.addEdge("out", "the");
        graph.addEdge("the", "answer");
        graph.addEdge("civilizations", "to");
    }

    private void printResult(Integer caseID,String caseName, List<String> expected, List<String> actual) {
        System.out.println("==== " + "TC" + caseID + ":"+ caseName + " ====");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);
        System.out.println();
    }

    @Test
    public void testBothWordsExist_OneBridgeWord() {
        List<String> result = graph.findBridgeWords("explore", "new");
        List<String> expected = List.of("strange");
        printResult(1,"Both words exist, one bridge word", expected, result);
        assertEquals(expected, result);
    }

    @Test
    public void testBothWordsExist_OneBridgeWord_2() {
        List<String> result = graph.findBridgeWords("and", "civilizations");
        List<String> expected = List.of("new");
        printResult(2,"Both words exist, one bridge word", expected, result);
        assertEquals(expected, result);
    }


    @Test
    public void testBothWordsExist_MultipleBridgeWords() {
        List<String> result = graph.findBridgeWords("to", "out");
        List<String> expected = List.of("find", "seek");
        Collections.sort(result);  // 因为 HashMap 顺序不确定
        printResult(3,"Both words exist, multiple bridge words", expected, result);
        assertEquals(expected, result);
    }

    @Test
    public void testBothWordsExist_NoBridgeWord() {
        List<String> result = graph.findBridgeWords("the", "answer");
        List<String> expected = List.of();
        printResult(4,"Both words exist, no bridge word", expected, result);
        assertEquals(expected, result);
    }

    @Test
    public void testBothWordsExist_NoBridgeWord() {
        List<String> result = graph.findBridgeWords("the", "answer");
        List<String> expected = List.of();
        printResult(4,"Both words exist, no bridge word", expected, result);
        assertEquals(expected, result);
    }

    @Test
    public void testWord2NotExist() {
        List<String> result = graph.findBridgeWords("to", "banana");
        List<String> expected = List.of("no word 2");
        printResult(6,"Word2 not in graph", expected, result);
        assertEquals(expected, result);
    }

    @Test
    public void testBothWordsNotExist() {
        List<String> result = graph.findBridgeWords("apple", "banana");
        List<String> expected = List.of("no word 1 and no word 2");
        printResult(7,"Both words not in graph", expected, result);
        assertEquals(expected, result);
    }
}
