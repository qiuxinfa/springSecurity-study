package com.qxf.service.impl;

import com.qxf.mapper.PermissionMapper;
import com.qxf.mapper.RoleMapper;
import com.qxf.pojo.Permission;
import com.qxf.pojo.Role;
import com.qxf.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/12
 * @Description: com.qxf.service.impl
 */
@Service
public class PermissionServiceImpl implements PermissionService{

    private Map<String, Collection<ConfigAttribute>> permissionMap;
    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    //构造函数执行之后执行该函数
    @PostConstruct
    public void initPermissionMap(){
        permissionMap = new HashMap<>();
        List<Permission> permissions = permissionMapper.getAllPermission();
        if (permissions != null && permissions.size() > 0){
            for (Permission permission : permissions){
                //根据permission id查找访问该url需要的角色列表
                List<Role> roles = roleMapper.getRoleListByPermissionId(permission.getId());
                Collection<ConfigAttribute> attributes = new ArrayList<>();
                for (Role role : roles){
                    //将角色封装成ConfigAttribute对象
                    ConfigAttribute attribute = new SecurityConfig(role.getName());
                    attributes.add(attribute);
                }
                permissionMap.put(permission.getUrl(),attributes);
            }
        }
    }

    @Override
    public List<Permission> getAllPermission() {
        return permissionMapper.getAllPermission();
    }

    @Override
    public Map<String, Collection<ConfigAttribute>> getPermissionMap() {
        if (permissionMap == null || permissionMap.size()==0){
            initPermissionMap();
        }
        return permissionMap;
    }
}
