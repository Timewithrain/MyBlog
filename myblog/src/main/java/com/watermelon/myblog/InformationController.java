package com.watermelon.myblog;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.watermelon.entity.Resource;
import com.watermelon.entity.User;
import com.watermelon.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class InformationController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping({"/information","/information.html"})
    public String picture(@PageableDefault(sort={"uploadTime"},direction=Sort.Direction.ASC) Pageable pageable, Model model){
        model.addAttribute("page",resourceService.listPicture(pageable));
        return "/admin/information";
    }

    @RequestMapping("/picture/upload")
    public String uploadPicture(MultipartFile file, Model model, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        resourceService.addPicture(file,user);
        return "/admin/information";
    }

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
