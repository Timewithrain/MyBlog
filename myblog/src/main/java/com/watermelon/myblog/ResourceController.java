package com.watermelon.myblog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class ResourceController {

//    @Value("${filePath}")
    private String filePath = "D:/Programs/MyBlog/upfiles/";

    @RequestMapping("/resource.html")
    public String resource(){
        return "/admin/resource";
    }

    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取扩展名
        String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath+fileName));
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();
        return "redirect:/admin/resource.html";
    }

}
