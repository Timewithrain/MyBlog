package com.watermelon.myblog;

import com.watermelon.entity.User;
import com.watermelon.service.ResourceService;
import com.watermelon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class InformationController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserService userService;

    @GetMapping({"/information","/information.html"})
    public String picture(Model model, HttpSession session){
        Long id = ((User)session.getAttribute("user")).getId();
        User user = userService.getUser(id);
        model.addAttribute("user",user);
        return "/admin/information";
    }

//    @RequestMapping("/picture/upload")
//    public String uploadPicture(MultipartFile file, Model model, HttpSession session) throws IOException {
//        User user = (User) session.getAttribute("user");
//        resourceService.addPicture(file,user);
//        return "redirect:/admin/information";
//    }

//    @GetMapping("/picture/delete/{id}")
//    public String delete(@PathVariable Long id){
//        Resource resource = resourceService.getResource(id);
//        File file = new File(resource.getPath()+resource.getName());
//        if (file.exists()){
//            file.delete();
//        }
//        resourceService.deleteResource(id);
//        return "redirect:/admin/information";
//    }
}
