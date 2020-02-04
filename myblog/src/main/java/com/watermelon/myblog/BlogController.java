package com.watermelon.myblog;

import com.watermelon.entity.Blog;
import com.watermelon.service.BlogService;
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

    @RequestMapping("/blogs")
    public String blog(@PageableDefault(size=10,sort="createTime",direction=Sort.Direction.ASC) Pageable pageable, Blog blog, Model model){
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        return "/admin/admin";
    }

}
