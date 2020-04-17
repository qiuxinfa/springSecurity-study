package com.qxf.config;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/17
 * @Description: com.qxf.config
 */
@Component
public class BaseResourceControlTest {
    /**
     * 判断请求的url是否有权访问
     */
    public boolean canAccess(HttpServletRequest request, Authentication authentication) {
        boolean flag = false;

        //如果没有通过认证，则不能访问， anonymousUser是springSecurity放入的匿名用户
        Object principal = authentication.getPrincipal();
        if(principal == null || "anonymousUser".equals(principal)) {
            return flag;
        }

        //匿名访问的url，在之前配置了，所以这里不通过
        if(authentication instanceof AnonymousAuthenticationToken){
            return flag;
        }

        //用户拥有的权限集合，也就是在UserServiceImpl放入的权限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        //URL规则匹配，判断用户是否权限集合中，是否包含请求的url
        AntPathRequestMatcher matcher;
        String resUrl = "";
        if (authorities != null && authorities.size() > 0){
            for(GrantedAuthority grantedAuthority : authorities) {
                resUrl = grantedAuthority.getAuthority();
                matcher = new AntPathRequestMatcher(resUrl);
                if(matcher.matches(request)) {
                    //匹配成功，返回true
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}
