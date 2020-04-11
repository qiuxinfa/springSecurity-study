package com.qxf.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/11
 * @Description: 认证成功后的回调
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    //spring会自动注册一个ObjectMapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("登录成功："+authentication.getName());
        //设置返回的格式及其编码
        response.setContentType("application/json;charset=UTF-8");
        //将已认证信息转换为json格式写到页面
        response.getWriter().write("登录成功："+objectMapper.writeValueAsString(authentication));
    }
}
