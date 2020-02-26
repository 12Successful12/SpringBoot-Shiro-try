package com.hzj.shiro.controller;

import com.hzj.shiro.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    /**
     * 欢迎页面映射
     * @return
     */
    @GetMapping("/")
    public String welcome(){
        return "welcome";
    }

    /**
     * 登录页面映射
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/main")
    public String main() {
        return "pages/main";
    }

    /**
     * （Level1）普通武林秘籍映射
     * @param path
     * @return
     */
    @GetMapping("/level1/{path}")
    public String level1(@PathVariable("path") String path){
        return "pages/level1/"+path;
    }

    /**
     * （Level2）高级武林秘籍
     * @param path
     * @return
     */
    @GetMapping("/level2/{path}")
    public String level2(@PathVariable("path") String path) {
        return "pages/level2/"+path;
    }

    /**
     * （Level3）绝世武林秘籍
     * @param path
     * @return
     */
    @GetMapping("/level3/{path}")
    public String level3(@PathVariable("path") String path){
        return "pages/level3/"+path;
    }

    /**
     * 使用 Shiro 编写认证操作
     */
    @PostMapping("/loginForm")
    public String loginForm(/*@RequestParam("username") String username,
                            @RequestParam("password") String password,*/
            User user, Model model){
        //  1、获取 Subject
        Subject currentUser = SecurityUtils.getSubject();
        //  2、封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            //  3、执行登录
            currentUser.login(token);
            //  记住我
            if(user.getRememberme() != null){
                token.setRememberMe(true);
            } else {
                token.setRememberMe(false);
            }
            return "redirect:/user/main";
        } catch (AuthenticationException e) {
            // 这里有一点需要注意：/login 若这个路径使用的是@GetMapping，那么将会报错，说
            // Resolved [org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'POST' not supported]
            model.addAttribute("msg","账号或密码错误！");
            return "forward:/login";
        }
    }
}
