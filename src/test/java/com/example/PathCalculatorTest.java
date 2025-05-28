package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PathCalculatorTest {

    private Graph graph;
    private PathCalculator calculator;

    @BeforeEach
    public void setUp() {
        graph = new Graph();

        // 构建图（完全按你提供的结构）
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

        calculator = new PathCalculator(graph);
    }

    private List<String> captureDisplayOutput(String start, String end) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        calculator.displayShortestPath(start, end);

        System.setOut(originalOut);
        return Arrays.asList(outputStream.toString().split("\\R"));
    }

    private void printResult(Integer caseID, String caseName, List<String> expected, List<String> actual) {
        System.out.println("==== " + "TC" + caseID + ":" + caseName + " ====\nExpected: " + expected + "\nActual:   " + actual + "\n");
    }

    private void assertOutputContainsAll(List<String> actual, List<String> expectedLines) {
        for (String expected : expectedLines) {
            assertTrue(actual.stream().anyMatch(line -> line.contains(expected)), "Missing expected line: " + expected);
        }
    }

    private void assertNoUnexpectedPaths(List<String> actual, List<String> expectedLines) {
        List<String> actualPaths = extractPathsFromOutput(actual);
        for (String path : actualPaths) {
            assertTrue(expectedLines.contains(path),
                    "Unexpected path found in output: " + path);
        }
    }

    private List<String> extractPathsFromOutput(List<String> output) {
        List<String> paths = new ArrayList<>();
        for (String line : output) {
            // 用简单正则判断是否是路径行，形如：单词 -> 单词 -> 单词
            if (line.trim().matches("^([\\w]+\\s*->\\s*)+[\\w]+$")) {
                paths.add(line.trim());
            }
        }
        return paths;
    }

    @Test
    public void testTC1_ToToLife() {
        List<String> output = captureDisplayOutput("strange", "and");
        List<String> expectedLines = List.of("strange -> new -> life -> and");
        printResult(1, "strange to seek",expectedLines,output);
        assertOutputContainsAll(output, expectedLines);
        assertNoUnexpectedPaths(output, expectedLines);
    }

    @Test
    public void testTC2_ToToAll() {
        List<String> output = captureDisplayOutput("to", null);
        List<String> expectedLines = List.of(
                "展示从 \"to\" 到其他单词的最短路径：",
                "to -> seek -> out -> new",
                "to -> find -> out -> new",
                "to -> explore -> strange -> new",
                "to -> seek -> out -> new -> worlds",
                "to -> find -> out -> new -> worlds",
                "to -> explore -> strange -> new -> worlds",
                "to -> explore",
                "to -> seek",
                "to -> explore -> strange",
                "to -> seek -> out -> new -> life",
                "to -> find -> out -> new -> life",
                "to -> explore -> strange -> new -> life",
                "to -> seek -> out",
                "to -> find -> out",
                "to -> seek -> out -> the",
                "to -> find -> out -> the",
                "to -> seek -> out -> the -> answer",
                "to -> find -> out -> the -> answer",
                "to -> find",
                "to -> seek -> out -> new -> life -> and",
                "to -> find -> out -> new -> life -> and",
                "to -> explore -> strange -> new -> life -> and",
                "to -> seek -> out -> new -> civilizations",
                "to -> find -> out -> new -> civilizations",
                "to -> explore -> strange -> new -> civilizations"
        );
        printResult(2, "to to all", expectedLines, output);
        assertOutputContainsAll(output, expectedLines);
        assertNoUnexpectedPaths(output, expectedLines);

    }

    @Test
    public void testTC3_UnknownToLife() {
        List<String> output = captureDisplayOutput("unknown", "life");
        List<String> expectedLines = List.of("No path found between \"unknown\" and \"life\".");
        printResult(3, "unknown to life", expectedLines, output);
        assertOutputContainsAll(output, expectedLines);
        assertNoUnexpectedPaths(output, expectedLines);
    }

    @Test
    public void testTC4_ToToUnknown() {
        List<String> output = captureDisplayOutput("to", "unknown");
        List<String> expectedLines = List.of("No path found between \"to\" and \"unknown\".");
        printResult(4, "to to unknown", expectedLines, output);
        assertOutputContainsAll(output, expectedLines);
        assertNoUnexpectedPaths(output, expectedLines);
    }

    @Test
    public void testTC5_ToToCivilizations() {
        List<String> output = captureDisplayOutput("to", "civilizations");
        List<String> expectedLines = List.of(
                "to -> find -> out -> new -> civilizations",
                "to -> seek -> out -> new -> civilizations",
                "to -> explore -> strange -> new -> civilizations"
        );
        printResult(5, "to to civilizations", expectedLines, output);
        assertOutputContainsAll(output, expectedLines);
        assertNoUnexpectedPaths(output, expectedLines);
    }

    @Test
    public void testTC6_OutToAnswer() {
        List<String> output = captureDisplayOutput("out", "answer");
        List<String> expectedLines = List.of("out -> the -> answer");
        printResult(6, "out to answer", expectedLines, output);
        assertOutputContainsAll(output, expectedLines);
        assertNoUnexpectedPaths(output, expectedLines);
    }

    @Test
    public void testTC7_AndToAnswer() {
        List<String> output = captureDisplayOutput("answer", "and");
        List<String> expectedLines = List.of("No path found between \"answer\" and \"and\".");
        printResult(7, "and to answer", expectedLines, output);
        assertOutputContainsAll(output, expectedLines);
        assertNoUnexpectedPaths(output, expectedLines);
    }
}
