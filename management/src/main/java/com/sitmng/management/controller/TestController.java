package com.sitmng.management.controller;


import com.sitmng.management.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/test")
    public ResponseEntity<Map<String,String>> test() {
        long count = departmentService.getAllDepartments().size();
        Map<String,String> map = new HashMap<>();
        if (count > 0) {
            map.put("database", "working");
        }else{
            map.put("database", "error");
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
