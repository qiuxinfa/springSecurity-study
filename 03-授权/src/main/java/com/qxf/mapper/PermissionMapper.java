package com.qxf.mapper;

import com.qxf.pojo.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/12
 * @Description: com.qxf.mapper
 */
public interface PermissionMapper {
    @Select("select * from permission")
    List<Permission> getAllPermission();
}
