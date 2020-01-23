package com.watermelon.myblog;

import com.watermelon.entity.User;
import com.watermelon.service.LoginService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping("/admin")
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session){
        LoginService loginService = new LoginService();
        User user = loginService.checkUser(username,password);
        System.out.println("login");
        if(user!=null){
            //删除密码以后再传入session，避免密码泄露
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            //通过重定向的方式，执行loginPage()方法跳转到login页面，
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String  logout(HttpSession session){
        session.setAttribute("user",null);
        return "redirect:/admin";
    }
}
