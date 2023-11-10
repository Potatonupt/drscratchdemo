package com.example.demo.service;

import com.example.demo.entity.AnalysisResult;
import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnalysisResultRepository extends CrudRepository<AnalysisResult, Long> {
    List<AnalysisResult> findByUserOrderByDateDesc(User user);
}

