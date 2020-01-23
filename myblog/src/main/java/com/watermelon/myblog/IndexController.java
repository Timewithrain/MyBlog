package com.watermelon.myblog;

import com.watermelon.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
//        String blog = null;
//        if(blog==null){
//            throw new NotFoundException("page is not found");
//        }
        int a = 9/0;
        System.out.println("-----------index-------------");
        return "index";
    }

    @GetMapping("/types.html")
    public String types(){
        System.out.println("-----------types-------------");
        return "types";
    }

    @GetMapping("/tags.html")
    public String tags(){
        System.out.println("-----------tags-------------");
        return "tags";
    }

    @GetMapping("/about.html")
    public String about(){
        System.out.println("-----------about-------------");
        return "about";
    }

}
