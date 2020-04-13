package com.qxf.mapper;

import com.qxf.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/11
 * @Description: com.qxf.mapper
 */
public interface UserMapper {

    @Select("select * from user where username=#{username}")
    User getUserByUsername(String username);

    List<User> getAllUser();

    @Insert("insert into user(username,password,enable) values(#{username},#{password},#{enable})")
    int addUser(User user);
}
