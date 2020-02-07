package com.watermelon.myblog;

import com.watermelon.entity.Blog;
import com.watermelon.entity.BlogQuery;
import com.watermelon.service.BlogService;
import com.watermelon.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @RequestMapping({"/admin.html","/admin"})
    public String blog(@PageableDefault(size=10,sort="createTime",direction=Sort.Direction.ASC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        return "/admin/admin";
    }

    @RequestMapping("/blogs/search")
    public String search(@PageableDefault(size=10,sort="createTime",direction=Sort.Direction.ASC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        return "/admin/admin :: list";
    }

    @RequestMapping("/blogs/add")
    public String add(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("blog",new Blog());
        return "/admin/blog-add";
    }
}
