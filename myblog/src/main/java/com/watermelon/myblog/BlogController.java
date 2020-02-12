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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String blog(@PageableDefault(size=5,sort="createTime",direction=Sort.Direction.ASC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        return "/admin/admin";
    }

    @RequestMapping("/admin/search")
    public String search(@PageableDefault(size=5,sort="updateTime",direction=Sort.Direction.ASC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        return "admin/admin :: list";
    }

    @RequestMapping({"/blogs/add","/blog-add.html"})
    public String add(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        Type type = new Type();
        Blog blog = new Blog();
        blog.setType(type);
        model.addAttribute("blog",blog);
        return "/admin/blog-add";
    }

    //添加Blog和修改Blog共享doAdd方法,根据是否包含id以区分添加和修改
    @PostMapping("/blogs/doAdd")
    public String doAdd(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User)session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagsId()));
        if(blog.getId()==null){
            Blog b = blogService.saveBlog(blog);
        }else{
            Blog b = blogService.updateBlog(blog);
        }
        return "redirect:/admin/admin.html";
    }

    //从后台获取博客内容加载并返回至前端页面
    @RequestMapping("/blogs-edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        //设置blog的tagIds，以在前端获取所有的tag
        Blog blog = blogService.getBlog(id);
        blog.setTagsId(blog.tagsToIds(blog.getTags()));
        model.addAttribute("blog",blog);
        return "admin/blog-add";
    }

    @GetMapping("/blogs-delete/{id}")
    public String delete(@PathVariable Long id,Model model){
        blogService.deleteBlog(id);
        return "redirect:/admin/admin.html";
    }

}
