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
                        try
                        {
                            // 初始化 Jackson ObjectMapper
                            ObjectMapper objectMapper = new ObjectMapper();

                            // 解析 JSON 数据
                            JsonNode rootNode = objectMapper.readTree(json);

                            // 初始化计数器
                            int totalIfStatementCount = 0;

                            // 遍历每个角色
                            JsonNode targets = rootNode.get("targets");
                            for (JsonNode target : targets) {
                                if (!target.has("blocks")) {
                                    continue;
                                }

                                int ifStatementCount = 0;

                                // 遍历角色的blocks字段，查找"opcode"为"control_if"的块
                                JsonNode blocks = target.path("blocks");
                                for (JsonNode block : blocks) {
                                    if (block.has("opcode") && block.get("opcode").asText().equals("control_if")) {
                                        ifStatementCount++;
                                    }
                                }

                                // 打印每个角色的if语句数量
                                System.out.println("角色 '" + target.get("name").asText() + "' 的if语句数量: " + ifStatementCount);

                                // 累加到总计数器
                                totalIfStatementCount += ifStatementCount;
                            }

                            // 打印总if语句的数量
                            System.out.println("总if语句的数量: " + totalIfStatementCount);


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
