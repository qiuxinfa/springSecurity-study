package com.qxf.mapper;

import com.qxf.pojo.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/12
 * @Description: com.qxf.mapper
 */
public interface RoleMapper {
    //根据用户id，查询角色列表
    @Select("select r.id,r.name from role r,user_role ur,user u where r.id=ur.role_id and ur.user_id=u.id and u.id=#{userId}")
    List<Role> getRoleListByUserId(Integer userId);

    //根据权限id，查询角色列表
    @Select("select r.id,r.name from role r,role_permission rp,permission p where r.id=rp.role_id and rp.permission_id=p.id and p.id=#{permissionId}")
    List<Role> getRoleListByPermissionId(Integer permissionId);

}
