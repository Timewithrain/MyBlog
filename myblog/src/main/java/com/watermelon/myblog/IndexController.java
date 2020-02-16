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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping({"/","/index.html"})
    public String index(@PageableDefault(size=5,sort={"updateTime"},direction=Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTopType(6));
        model.addAttribute("tags",tagService.listTopTag(10));
        model.addAttribute("recommendBlog",blogService.listTopBlog(10));
        System.out.println("-----------index-------------");
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size=5,sort={"updateTime"},direction=Sort.Direction.DESC) Pageable pageable, @RequestParam String query, Model model){
        //通过搜索框内传入的内容调用数据库like查询检索相关的blog
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        System.out.println("-----------blog-------------");
        model.addAttribute("blog",blogService.getBlog(id));
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
