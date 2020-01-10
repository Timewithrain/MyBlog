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
        System.out.println("-----------index-------------");
        return "index";
    }

}
