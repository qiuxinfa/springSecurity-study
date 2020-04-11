package com.qxf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/11
 * @Description: com.qxf
 */
@SpringBootApplication
@MapperScan(basePackages = "com.qxf.mapper")
public class SelfAuthenticationApp {
    public static void main(String[] args) {
        SpringApplication.run(SelfAuthenticationApp.class,args);
    }
}
