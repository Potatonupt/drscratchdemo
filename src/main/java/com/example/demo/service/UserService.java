package com.example.demo.service;

import com.example.demo.entity.AnalysisResult;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    public User currentUser;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnalysisResultRepository analysisResultRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(String username, String password) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        currentUser=user;
        return user;
    }


    public AnalysisResult saveAnalysisResult(User user, int[] result) {
        String resultStr = Arrays.toString(result);

        AnalysisResult analysisResult = new AnalysisResult();
        analysisResult.setUser(user);
        analysisResult.setResult(resultStr);
        analysisResult.setDate(new Date());

        return analysisResultRepository.save(analysisResult);
    }

    public List<AnalysisResult> getHistoryResults(User user) {
        // 根据用户获取历史分析结果的逻辑，可能是查询数据库等
        return analysisResultRepository.findByUserOrderByDateDesc(user);
    }
}
