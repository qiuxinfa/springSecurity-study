package com.qxf.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/11
 * @Description: 认证失败回调
 */
@Component
public class MyAuthenticationFailuerHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        System.out.println("登录失败："+e.getMessage());
        //设置返回的格式及其编码
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("登录失败："+e.getMessage());
    }
}
