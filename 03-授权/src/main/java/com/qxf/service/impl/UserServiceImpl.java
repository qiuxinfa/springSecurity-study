package com.qxf.service.impl;

import com.qxf.mapper.RoleMapper;
import com.qxf.mapper.UserMapper;
import com.qxf.pojo.Permission;
import com.qxf.pojo.User;
import com.qxf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/11
 * @Description: com.qxf.service
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(username);
        if (user != null){
            user.setRoles(roleMapper.getRoleListByUserId(user.getId()));
        }
        //下面是为了测试 基于 资源的访问控制，这里为了方便，就不查数据库了
        List<Permission> permissions = new ArrayList<>(2);
        Permission p = new Permission(11,"/user/me");
        permissions.add(p);
        if ("qxf".equals(username)){
            Permission p2 = new Permission(22,"/student/list");
            permissions.add(p2);
        }
        user.setPermissions(permissions);

        return user;
    }

    @Override
    public List<User> getAllUser(){
        return userMapper.getAllUser();
    }
}
