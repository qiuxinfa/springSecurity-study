package com.qxf.controller;

import com.qxf.pojo.User;
import com.qxf.validate.VerifyCode;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    //添加图形验证码功能
    @GetMapping("/imageCode")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        VerifyCode verifyCode = new VerifyCode();
        BufferedImage image = verifyCode.getImage();
        String code = verifyCode.getText();
        HttpSession session = request.getSession();
        session.setAttribute("image_code", code);
        //将图形验证码写入响应中去
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

}
