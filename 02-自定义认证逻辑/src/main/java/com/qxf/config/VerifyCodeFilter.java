package com.qxf.config;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/21
 * @Description: 验证码过滤器
 */
@Component
public class VerifyCodeFilter extends GenericFilterBean {
    private String defaultFilterProcessUrl = "/login.html";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if ("POST".equalsIgnoreCase(request.getMethod()) && defaultFilterProcessUrl.equals(request.getServletPath())) {
            // 获取用户输入验证码
            String requestCaptcha = request.getParameter("imageCode");
            // 获取session中的验证码
            String genCaptcha = (String) request.getSession().getAttribute("image_code");
            //移除验证码，保证session中每次取都是新的验证码
            request.removeAttribute("image_code");
            if (StringUtils.isEmpty(requestCaptcha))
                throw new AuthenticationServiceException("验证码不能为空!");
            if (!genCaptcha.toLowerCase().equals(requestCaptcha.toLowerCase())) {
                throw new AuthenticationServiceException("验证码错误!");
            }
        }
        chain.doFilter(request, response);
    }
}