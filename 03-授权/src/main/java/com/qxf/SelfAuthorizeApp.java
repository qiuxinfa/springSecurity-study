package com.qxf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/12
 * @Description: com.qxf
 */
@SpringBootApplication
@MapperScan(basePackages = "com.qxf.mapper")
public class SelfAuthorizeApp {
    public static void main(String[] args) {
        SpringApplication.run(SelfAuthorizeApp.class,args);
    }
}
