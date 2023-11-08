package com.example.demo.analysis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonAnalyzer
{
    public static int[] analyzeSb3Project(String json)
    {
        int[] results = new int[2];  // 创建一个包含两个整数的数组，用于存储结果

        //if
        int ifStatementCount = 0;
        int totalIfStatementCount = 0;

        int motionNumber = 0;

        try
        {
            // 初始化 Jackson ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // 解析 JSON 数据
            JsonNode rootNode = objectMapper.readTree(json);

            // 遍历每个角色
            JsonNode targets = rootNode.get("targets");
            for (JsonNode target : targets)
            {
                if (!target.has("blocks"))
                {
                    continue;
                }

                // 遍历角色的blocks字段
                JsonNode blocks = target.path("blocks");
                for (JsonNode block : blocks)
                {
                    if (block.has("opcode") && block.get("opcode").asText().equals("control_if"))
                    {
                        ifStatementCount++;
                    }
                    if (block.has("opcode") && block.get("opcode").asText().startsWith("motion_"))
                    {
                        motionNumber++;
                    }
                }

                // 累加到总计数器
                totalIfStatementCount += ifStatementCount;
            }

            results[0] = totalIfStatementCount;
            results[1] = motionNumber;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return results;
    }

}

