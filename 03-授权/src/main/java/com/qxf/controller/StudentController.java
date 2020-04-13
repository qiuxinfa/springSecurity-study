package com.qxf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/12
 * @Description: com.qxf.controller
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @GetMapping("/list")
    public String list(){
        return "有权限访问学生列表";
    }
}

