package com.example.demo.controller;

import com.example.demo.analysis.JsonAnalyzer;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Controller
public class FileUploadController
{
    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file, HttpSession session)
    {
        Map<String, Object> response = new HashMap<>();
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
                        int[] result = JsonAnalyzer.analyzeSb3Project(json);
                        session.setAttribute("resultArray", result);
//                        for (var i : result)
//                        {
//                            System.out.println(i+"\n");
//                        }
                    }
                }
                zipInputStream.close();
                response.put("success", true);
            }
            catch (IOException e)
            {
                response.put("success", false);
                response.put("error", "File analysis failed: " + e.getMessage());
            }
        }
        else
        {
            response.put("success", false);
            response.put("error", "Please select a file to upload.");
        }
        return response;
    }
}
