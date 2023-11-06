package com.example.demo.analysis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class test {
    public static void main(String[] args) {
        try {
            // JSON字符串
            String jsonString = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}";

            // 创建一个ObjectMapper对象
            ObjectMapper objectMapper = new ObjectMapper();

            // 将JSON字符串解析为JsonNode对象
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // 从JsonNode中提取数据
            String name = jsonNode.get("name").asText();
            int age = jsonNode.get("age").asInt();
            String city = jsonNode.get("city").asText();

            // 输出解析后的数据
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("City: " + city);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

