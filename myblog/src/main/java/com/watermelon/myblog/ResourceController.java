package com.watermelon.myblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class ResourceController {

    @RequestMapping("/resource.html")
    public String resource(){
        return "/admin/resource";
    }

    @RequestMapping("/upload")
    public String upload(MultipartFile file){
        String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        return "redirect:/admin/resource.html";
    }

}
