package com.example;

import java.io.*;
import java.util.*;

public class TextParser {
        /**
     * 解析文本文件，将内容转换为单词列表
     * 该方法首先读取指定文件的内容，然后移除所有非字母字符并转换为小写，最后将内容分割成单词列表
     * 
     * @param filename 要解析的文件的名称
     * @return 文件内容转换后的单词列表
     * @throws IOException 如果文件读取过程中发生错误
     */
    public static List<String> parseTextFile(String filename) throws IOException {
        // 使用StringBuilder来拼接文件内容
        StringBuilder content = new StringBuilder();
        // 使用try-with-resources语句自动管理资源，读取文件内容
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // 循环读取文件中的每一行，直到文件结束
            while ((line = reader.readLine()) != null) {
                // 将每一行内容追加到StringBuilder中，并在行末添加空格
                content.append(line).append(" ");
            }
        }
        // 移除所有非字母字符并转换为小写，以进行后续的单词分割处理
        String sanitized = content.toString().replaceAll("[^a-zA-Z ]", " ").toLowerCase();
        // 分割处理后的字符串，转换为单词列表并返回
        return Arrays.asList(sanitized.trim().split("\\s+"));
    }
    public static List<String> parseTextFromString(String text) {
        // 移除所有非字母字符，并将文本转换为小写
        String sanitized = text.replaceAll("[^a-zA-Z ]", " ").toLowerCase();
        String[] words = text.split("\\s+");  // 以空格分词
        return Arrays.asList(words);
    }
}

