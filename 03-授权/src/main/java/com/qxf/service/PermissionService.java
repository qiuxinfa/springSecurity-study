package com.qxf.service;

import com.qxf.pojo.Permission;
import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/12
 * @Description: com.qxf.service
 */
public interface PermissionService {
    List<Permission> getAllPermission();

    Map<String, Collection<ConfigAttribute>> getPermissionMap();
}
