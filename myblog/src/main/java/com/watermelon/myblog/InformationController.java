package com.watermelon.myblog;

import com.watermelon.entity.Resource;
import com.watermelon.entity.User;
import com.watermelon.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.File;

@Controller
@RequestMapping("/admin")
public class InformationController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping({"/information","/information.html"})
    public String picture(Model model, HttpSession session){
        model.addAttribute("user",(User)session.getAttribute("user"));
        return "/admin/information";
    }

//    @RequestMapping("/picture/upload")
//    public String uploadPicture(MultipartFile file, Model model, HttpSession session) throws IOException {
//        User user = (User) session.getAttribute("user");
//        resourceService.addPicture(file,user);
//        return "redirect:/admin/information";
//    }

    @GetMapping("/picture/delete/{id}")
    public String delete(@PathVariable Long id){
        Resource resource = resourceService.getResource(id);
        File file = new File(resource.getPath()+resource.getName());
        if (file.exists()){
            file.delete();
        }
        resourceService.deleteResource(id);
        return "redirect:/admin/information";
    }
}
