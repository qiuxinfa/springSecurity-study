package com.qxf.service.impl;

import com.qxf.mapper.RoleMapper;
import com.qxf.mapper.UserMapper;
import com.qxf.pojo.User;
import com.qxf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        return user;
    }

    @Override
    public List<User> getAllUser(){
        return userMapper.getAllUser();
    }
}
