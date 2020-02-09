package com.watermelon.myblog;

import com.watermelon.entity.Blog;
import com.watermelon.entity.BlogQuery;
import com.watermelon.entity.Type;
import com.watermelon.entity.User;
import com.watermelon.service.BlogService;
import com.watermelon.service.TagService;
import com.watermelon.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @RequestMapping({"/admin.html","/admin"})
    public String blog(@PageableDefault(size=3,sort="createTime",direction=Sort.Direction.ASC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        return "/admin/admin";
    }

    @RequestMapping("/blogs/search")
    public String search(@PageableDefault(size=3,sort="createTime",direction=Sort.Direction.ASC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        return "/admin/admin :: list";
    }

    @RequestMapping({"/blogs/add","/blog-add.html"})
    public String add(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return "/admin/blog-add";
    }

    @PostMapping("/blogs/doAdd")
    public String doAdd(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User)session.getAttribute("user"));
        System.out.println(blog.getTitle());
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagsId()));
        Blog b = blogService.saveBlog(blog);
        return "redirect:/admin/admin.html";
    }
}
