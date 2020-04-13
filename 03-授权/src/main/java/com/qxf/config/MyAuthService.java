package com.qxf.config;

import com.qxf.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/12
 * @Description: 通过扩展access()的SpEL表达式：自定义授权逻辑
 */
@Component
public class MyAuthService  {

    @Autowired
    private PermissionService permissionService;

    /**
     * 判断请求的url是否有权访问
     */
    public boolean canAccess(HttpServletRequest request, Authentication authentication) {
        boolean b = false;

        //如果没有通过认证，则不能访问， anonymousUser是springSecurity放入的匿名用户
        Object principal = authentication.getPrincipal();
        if(principal == null || "anonymousUser".equals(principal)) {
            return b;
        }

        //匿名访问的url，在之前配置了，所以这里不通过
        if(authentication instanceof AnonymousAuthenticationToken){
            return b;
        }

        //数据库中所有url，对应的权限
        Map<String,Collection<ConfigAttribute>> map =  permissionService.getPermissionMap();

        Collection<ConfigAttribute> configAttributes = null;
        String resUrl;
        //URL规则匹配，判断请求的url是否在权限表中配置
        AntPathRequestMatcher matcher;
        for(Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
            resUrl = it.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if(matcher.matches(request)) {
                configAttributes =  map.get(resUrl);
                break;
            }
        }

        //权限表中没有配置该url，也不是匿名可以访问的url，拦截
        if(configAttributes == null || configAttributes.size() ==0) {
            return b;
        }

        //请求的url在数据库中有配置，则判断当前用户是否拥有相应的权限，在这里其实是判断角色
        ConfigAttribute cfa  = null;
        String needRole = null;
        for(Iterator<ConfigAttribute> it = configAttributes.iterator();it.hasNext();) {
            cfa = it.next();
            needRole = cfa.getAttribute();
            for(GrantedAuthority grantedAuthority:authentication.getAuthorities()) {
                if(needRole.equals(grantedAuthority.getAuthority())) {
                    System.out.println("needRole = "+needRole);
                    //拥有其中的一个角色就可以访问了
                    b = true;
                    break;
                }
            }
        }
        return b;
    }

}
