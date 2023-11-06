package com.example.demo.analysis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonAnalyzer {


    public static double calculateAnalysisScore(int age, double score) {
        // 这里可以根据你的分析逻辑来计算分数
        // 例如，根据年龄和分数来决定分数
        return age * score / 100;
    }
}

