package com.huike.controller;

import com.huike.domain.AdminUser;
import com.huike.vo.AdminUserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LogInController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    //登录
    @RequestMapping(value = "/submitLogin.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> logIn(AdminUserVO adminUser) {
        Map<String, Object> map = new HashMap<>();
        System.out.println("name="+adminUser.getUsername());
        try {
            Subject subject = SecurityUtils.getSubject();
            if (adminUser != null) {
                UsernamePasswordToken token = new UsernamePasswordToken(adminUser.getUsername(), adminUser.getPassword());
                subject.login(token);
                if (adminUser.getRememberMe()){
                    token.setRememberMe(true);
                }
                map.put("status",200);
                map.put("message","登录成功");
             }

        }catch (UnknownAccountException ex){
            System.out.println("输入的账号不存在");
            map.put("status",500);
            map.put("message","登录失败");
        }catch (IncorrectCredentialsException ex){
            System.out.println("输入用户名密码不正确，请重新输入");
            map.put("status",500);
            map.put("message","登录失败");
        }
        return map;
    }

    //登录失败返回
    @RequestMapping("/perms/error")
    public String permsError() {
        System.out.println("权限不足");
        return "perm_error";
    }

    //登出
    @GetMapping("/logout")
    public String logOut(){
        Subject subject;
        subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

}
