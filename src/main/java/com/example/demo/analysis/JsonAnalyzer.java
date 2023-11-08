package com.example.demo.analysis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonAnalyzer
{
    public static int[] analyzeSb3Project(String json)
    {
        int[] results = new int[20];  // 创建一个包含两个整数的数组，用于存储结果

        //if
        int ifStatementCount = 0;
        int totalIfStatementCount = 0;

        int motionCount = 0;
        int looksCount = 0;
        int soundCount = 0;
        int eventCount = 0;
        int controlCount = 0;
        int sensingCount = 0;
        int operatorCount = 0;
        int dataCount = 0;
        int proceduresCount = 0;
        int penCount = 0;
        int canvasCount = 0;
        int totalcodeCount=0;
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
                    if (block.has("opcode"))
                    {

                        totalcodeCount++;
//                        if (block.get("opcode").asText().equals("control_if"))
//                        {
//                            ifStatementCount++;
//                        }
                        if (block.get("opcode").asText().startsWith("motion_"))
                        {
                            motionCount++;
                        }
                        if (block.get("opcode").asText().startsWith("looks_"))
                        {
                            looksCount++;
                        }
                        if (block.get("opcode").asText().startsWith("sound_"))
                        {
                            soundCount++;
                        }
                        if (block.get("opcode").asText().startsWith("event_"))
                        {
                            eventCount++;
                        }
                        if (block.get("opcode").asText().startsWith("control_"))
                        {
                            controlCount++;
                        }
                        if (block.get("opcode").asText().startsWith("sensing_"))
                        {
                            sensingCount++;
                        }
                        if (block.get("opcode").asText().startsWith("operator_"))
                        {
                            operatorCount++;
                        }
                        if (block.get("opcode").asText().startsWith("procedures_"))
                        {
                            proceduresCount++;
                        }
                    }
                }

            }

            results[0] = totalcodeCount;
            results[1] = motionCount;
            results[2] = looksCount;
            results[3] = soundCount;
            results[4] = eventCount;
            results[5] = controlCount;
            results[6] = sensingCount;
            results[7] = operatorCount;
            results[8] = proceduresCount;


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return results;
    }

}

