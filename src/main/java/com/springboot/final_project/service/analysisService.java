package com.springboot.final_project.service;

import java.util.List;
import java.util.Map;

public interface analysisService {
    List<Integer> analysisHealth ();

    Map<String ,Object> analysisRecord(int option);
}
