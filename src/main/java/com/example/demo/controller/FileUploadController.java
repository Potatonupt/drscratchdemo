package com.example.demo.controller;

import com.example.demo.analysis.JsonAnalyzer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Controller
public class FileUploadController
{
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model)
    {
        if (file != null && !file.isEmpty())
        {
            try
            {
                ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
                ZipEntry entry;
                while ((entry = zipInputStream.getNextEntry()) != null)
                {
                    if (entry.getName().endsWith(".json"))
                    {
                        // JSON文件的名称以".json"为扩展名
                        StringBuilder jsonContent = new StringBuilder();
                        int read;
                        byte[] buffer = new byte[1024];
                        while ((read = zipInputStream.read(buffer, 0, buffer.length)) != -1)
                        {
                            jsonContent.append(new String(buffer, 0, read));
                        }

                        // 输出JSON文件内容
//                        System.out.println("JSON File Content: " + jsonContent.toString());

                        String json = jsonContent.toString();
//                        System.out.println(json);
//                        String json = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}";
                        try
                        {
                            // 初始化 Jackson ObjectMapper
                            ObjectMapper objectMapper = new ObjectMapper();

                            // 解析 JSON 数据
                            JsonNode rootNode = objectMapper.readTree(json);

                            // 获取 "targets" 节点
                            JsonNode targetsNode = rootNode.get("targets");
                            System.out.println(targetsNode.get(20));
//                            if (targetsNode.isArray())
//                            {
//                                for (JsonNode target : targetsNode)
//                                {
//                                    // 获取 "History" 节点
//                                    JsonNode historyNode = target.get("lists").get("zWw,J;#Qe7hmIOddL`xw-History-list");
//
//                                    if (historyNode.isArray())
//                                    {
//                                        for (JsonNode historyItem : historyNode)
//                                        {
//                                            String expression = historyItem.asText();
//
//                                            // 根据评分标准检查表达式
//                                            if (expression.contains("5+9") && expression.contains("9*8-9/9"))
//                                            {
//                                                // 符合评分标准，可以增加分数
//                                                // 这里可以根据评分标准定义你的评分逻辑
//                                                System.out.println("Found a high-quality expression: " + expression);
//                                            }
//                                        }
//                                    }
//                                }
//                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                }
                zipInputStream.close();
                model.addAttribute("message", "File analysis completed.");
            }
            catch (IOException e)
            {
                model.addAttribute("error", "File analysis failed: " + e.getMessage());
            }
        }
        else
        {
            model.addAttribute("error", "Please select a file to upload.");
        }
        return "uploadForm";
    }
}
