package com.qxf.pojo;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/12
 * @Description: com.qxf.pojo
 */
public class Role implements GrantedAuthority,Serializable {
    private Integer id;
    private String name;

    public Role(){}

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        //用角色名作为权限
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
