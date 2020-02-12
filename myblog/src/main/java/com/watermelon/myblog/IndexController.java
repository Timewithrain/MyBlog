package com.watermelon.myblog;

import com.watermelon.exception.NotFoundException;
import com.watermelon.service.BlogService;
import com.watermelon.service.TagService;
import com.watermelon.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping({"/","/index.html"})
    public String index(@PageableDefault(size=5,sort={"updateTime"},direction=Sort.Direction.ASC) Pageable pageable, Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTopType(6));
        model.addAttribute("tags",tagService.listTopTag(10));
//        String blog = null;
//        if(blog==null){
//            throw new NotFoundException("page is not found");
//        }
        System.out.println("-----------index-------------");
        return "index";
    }

    @GetMapping("/blog.html")
    public String blog(){
        System.out.println("-----------blog-------------");
        return "blog";
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
