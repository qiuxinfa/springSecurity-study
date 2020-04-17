package com.qxf.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/12
 * @Description: com.qxf.pojo
 */
public class Permission implements Serializable{
    private Integer id;
    private String name;
    private String url;
    private String description;
    private Integer pid;
    //一个权限对应多个角色
    private List<Role> roles = new ArrayList<>();

    public Permission(){

    }

    public Permission(Integer id,String url){
        this.id = id;
        this.url = url;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", pid=" + pid +
                '}';
    }
}
