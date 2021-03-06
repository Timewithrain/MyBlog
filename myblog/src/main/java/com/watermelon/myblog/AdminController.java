package com.watermelon.myblog;

import com.watermelon.entity.User;
import com.watermelon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    public UserService userService;

    @GetMapping("/admin")
    public String loginPage(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            return "redirect:/admin/admin";
        }else{
            return "/admin/login";
        }
    }

    @PostMapping("/admin/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        if(user!=null){
            //删除密码以后再传入session，避免密码泄露
            user.setPassword(null);
            session.setAttribute("user",user);
            //跳转至admin后台管理页面
            return "redirect:/admin/admin.html";
        }else{
            //当检测出用户输入错误则返回错误信息
            attributes.addFlashAttribute("message","用户名密码错误，请重新输入！");
            //通过重定向的方式，执行loginPage()方法跳转到login页面，
            return "redirect:/admin";
        }
    }

    @GetMapping("/admin/publish.html")
    public String publish(HttpSession session){
        System.out.println("-----------publish-------------");
        return "blog-add";
    }

    @GetMapping("/admin/logout")
    public String  logout(HttpSession session){
        session.setAttribute("user",null);
        return "redirect:/admin";
    }
}
