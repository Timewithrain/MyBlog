package com.watermelon.myblog;

import com.watermelon.entity.BlogQuery;
import com.watermelon.entity.Tag;
import com.watermelon.entity.Type;
import com.watermelon.service.BlogService;
import com.watermelon.service.TagService;
import com.watermelon.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        System.out.println("-----------index-------------");
        model.addAttribute("page",blogService.listBlogAndConvert(pageable));
        model.addAttribute("types",typeService.listTopType(6));
        model.addAttribute("tags",tagService.listTopTag(10));
        model.addAttribute("recommendBlog",blogService.listTopBlog(10));
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
        model.addAttribute("blog",blogService.getBlogAndConvert(id));
        return "blog";
    }

    @RequestMapping("/types/{id}")
    public String show(@PageableDefault(size=3, sort={"updateTime"}, direction=Sort.Direction.DESC) Pageable pageable,@PathVariable Long id, Model model){
        System.out.println("-----------types-------------");
        List<Type> types =  typeService.listTopType(100);
        if (id==-1){
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlogAndConvert(pageable,blogQuery));
        model.addAttribute("activeTypeId",id);
        return "types";
    }

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size=3,sort={"updateTime"},direction=Sort.Direction.DESC) Pageable pageable,@PathVariable Long id, Model model){
        System.out.println("-----------tags-------------");
        List<Tag> tags =  tagService.listTopTag(100);
        if (id==-1){
            id = tags.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTagId(id);
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlogByTagAndConvert(pageable,blogQuery));
        model.addAttribute("activeTagId",id);
        return "tags";
    }

    @GetMapping("/about.html")
    public String about(){
        System.out.println("-----------about-------------");
        return "about";
    }

}
