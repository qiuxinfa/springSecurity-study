package com.qxf.controller;

import com.qxf.pojo.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/11
 * @Description: com.qxf.controller
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/me")
    public Object getUserInfo(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    //只返回我们放入的信息
    @GetMapping ("/info")
    public User getCurrentUser(@AuthenticationPrincipal UserDetails userDetails){
        return (User) userDetails;
    }

    @PostMapping("/add")
    public String addUser(User user){
        return "注册成功";
    }

}
