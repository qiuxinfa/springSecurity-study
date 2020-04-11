package com.qxf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/11
 * @Description: com.qxf.controller
 */
@RestController
public class HelloSecurityController {

    @GetMapping("/hello")
    public String hello (){
        return "hello spring security";
    }
}
