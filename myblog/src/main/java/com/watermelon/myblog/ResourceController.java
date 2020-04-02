package com.watermelon.myblog;

import com.watermelon.entity.User;
import com.watermelon.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping({"/resource.html","/resource"})
    public String resource(@PageableDefault(sort={"uploadTime"},direction=Sort.Direction.ASC) Pageable pageable, Model model){
        model.addAttribute("page",resourceService.listResource(pageable));
        return "/admin/resource";
    }

    @RequestMapping("/resource/upload")
    public String upload(@RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        resourceService.addResource(file,user);
        return "redirect:/admin/resource.html";
    }

    @GetMapping("/resource/update/{id}")
    public String update(@PathVariable Long id){
        resourceService.updatePrivate(id);
        return "redirect:/admin/resource.html";
    }

    @RequestMapping("/resource/download/{id}")
    public ResponseEntity download(@PathVariable Long id) throws Exception{
        return resourceService.downloadFile(id);
    }

    @RequestMapping("/resource/search")
    public String search(@PageableDefault(sort={"uploadTime"},direction=Sort.Direction.ASC) Pageable pageable, Model model){
        model.addAttribute("page",resourceService.listResource(pageable));
        return "admin/resource :: list";
    }

    @GetMapping("/resource/delete/{id}")
    public String delete(@PathVariable Long id){
        resourceService.deleteResource(id);
        return "redirect:/admin/resource.html";
    }

}
