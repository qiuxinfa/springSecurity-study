package com.qxf.config;

import com.qxf.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/12
 * @Description: com.qxf.config
 */
@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private PermissionService permissionService;

    //返回请求url需要的权限
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        HttpServletRequest request = filterInvocation.getRequest();
        Map<String, Collection<ConfigAttribute>> permissionMap = permissionService.getPermissionMap();
        AntPathRequestMatcher  matcher;
        String dbUrl;
        for (Iterator<String> iterator=permissionMap.keySet().iterator();iterator.hasNext();){
            dbUrl = iterator.next();
            matcher = new AntPathRequestMatcher(dbUrl);
            if (matcher.matches(request)){
                System.out.println("匹配成功:"+request.getRequestURI());
                return permissionMap.get(dbUrl);
            }
        }
        //没有匹配成功，也可以指定特定的角色返回SecurityConfig.createList("ROLE_USER");
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true ;
    }
}
