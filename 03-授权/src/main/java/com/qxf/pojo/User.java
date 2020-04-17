package com.qxf.pojo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/11
 * @Description: com.qxf.pojo
 */
public class User implements UserDetails,Serializable{
    private Integer id;
    private String username;
    private String password;
    private Integer enable;    //账号是否有效：1有效，0无效
    //一个用户拥有多个角色
    private List<Role> roles = new ArrayList<>();

    //测试基于 资源的访问控制
    private List<Permission> permissions = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return getRoles();
        //下面是为了测试 基于 资源的访问控制
        List <SimpleGrantedAuthority> list = null;
        List<Permission> permissionList = getPermissions();
        if (permissionList !=null && permissionList.size() > 0){
            list = new ArrayList<>(permissionList.size());
            SimpleGrantedAuthority simpleGrantedAuthority = null;
            for (Permission p : permissionList){
                simpleGrantedAuthority = new SimpleGrantedAuthority(p.getUrl());
                list.add(simpleGrantedAuthority);
            }
        }
        return list;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable==1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enable=" + enable +
                '}';
    }
}
