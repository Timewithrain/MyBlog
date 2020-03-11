package com.watermelon.myblog;

import com.watermelon.DAO.ResourceRepository;
import com.watermelon.entity.Resource;
import com.watermelon.entity.User;
import com.watermelon.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class ResourceController {

    @Value("${system-params.file.path}")
    private String filePath;

    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/resource.html")
    public String resource(){
        return "/admin/resource";
    }

    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        resourceService.addResource(file,user);
        return "redirect:/admin/resource.html";
    }

    @RequestMapping("/download")
    public ResponseEntity download() throws Exception{
        FileSystemResource file = new FileSystemResource("D:/Programs/MyBlog/upfiles/计协图标.png");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Dispositon","attachment; filename=123.jpg");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

}
