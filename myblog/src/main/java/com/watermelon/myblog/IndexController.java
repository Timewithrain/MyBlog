package com.watermelon.myblog;

import com.watermelon.entity.*;
import com.watermelon.service.BlogService;
import com.watermelon.service.CommentService;
import com.watermelon.service.TagService;
import com.watermelon.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @GetMapping({"/","/index.html"})
    public String index(@PageableDefault(size=6,sort={"updateTime"},direction=Sort.Direction.DESC) Pageable pageable, Model model){
        System.out.println("-----------index-------------");
        model.addAttribute("page",blogService.listBlogAndConvert(pageable));
        model.addAttribute("types",typeService.listTopType(6));
        model.addAttribute("tags",tagService.listTopTag(10));
        model.addAttribute("recommendBlog",blogService.listTopBlog(10));
        return "index";
    }

    @PostMapping("/searchPage")
    public String search(@PageableDefault(size=3,sort={"updateTime"},direction=Sort.Direction.DESC) Pageable pageable, @RequestParam String query, HttpSession session, Model model){
        session.setAttribute("query",query);
        //通过搜索框内传入的内容调用数据库like查询检索相关的blog
        model.addAttribute("page",blogService.listBlogByStringAndCovert("%"+query+"%",pageable));
        return "search";
    }

    @GetMapping("/search")
    public String searchPage(@PageableDefault(size=3,sort={"updateTime"},direction=Sort.Direction.DESC) Pageable pageable, HttpSession session, Model model){
        String query = (String) session.getAttribute("query");
        model.addAttribute("page",blogService.listBlogByStringAndCovert("%"+query+"%",pageable));
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        System.out.println("-----------blog-------------");
        model.addAttribute("blog",blogService.getBlogAndConvert(id));
        model.addAttribute("comments",commentService.listCommentByBlogId(id));
        return "blog";
    }

    @GetMapping("/comments/{blogId}")
    public String comment(@PathVariable Long blogId,Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String postComment(Comment comment,HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAdminComment(true);
            comment.setAvatar(user.getAvatar());
            comment.setNickname(user.getNickname());
            comment.setEmail(user.getEmail());
        }else{
            comment.setAvatar("/img/default.png");
        }
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

    @PostMapping("/commentDelete")
    public String deleteComment(Comment comment, Model model){
        System.out.println("comment:" + comment.getId());
        System.out.println("blog:" + comment.getBlog().getId());
        commentService.deleteComment(comment.getId());
        model.addAttribute("comments",commentService.listCommentByBlogId(comment.getBlog().getId()));
        return "blog :: commentList";
    }

    @RequestMapping("/types/{id}")
    public String types(@PageableDefault(size=5, sort={"updateTime"}, direction=Sort.Direction.DESC) Pageable pageable,@PathVariable Long id, Model model){
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

    @GetMapping("/types/next/{id}")
    public String typegPageNext(@PathVariable Long id,Pageable pageable){
        int page = pageable.getPageNumber() + 1;
        return "redirect:/types/"+id+"/?page="+page;
    }

    @GetMapping("/types/last/{id}")
    public String typePageLast(@PathVariable Long id,Pageable pageable){
        int page = pageable.getPageNumber() - 1;
        return "redirect:/types/"+id+"/?page="+page;
    }

    @GetMapping({"/tags/{id}","/tasg{id}"})
    public String tags(@PageableDefault(size=5,sort={"updateTime"},direction=Sort.Direction.DESC) Pageable pageable,@PathVariable Long id, Model model){
        System.out.println("-----------tags-------------");
        List<Tag> tags =  tagService.listTopTag(100);
        if (id==-1){
            id = tags.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTagId(id);
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlogAndConvert(pageable,blogQuery));
        model.addAttribute("activeTagId",id);
        return "tags";
    }

    @GetMapping("/tags/next/{id}")
    public String tagPageNext(@PathVariable Long id,Pageable pageable){
        int page = pageable.getPageNumber() + 1;
        return "redirect:/tags/"+id+"/?page="+page;
    }

    @GetMapping("/tags/last/{id}")
    public String tagPageLast(@PathVariable Long id,Pageable pageable){
        int page = pageable.getPageNumber() - 1;
        return "redirect:/tags/"+id+"/?page="+page;
    }

    @GetMapping("/about.html")
    public String about(){
        System.out.println("-----------about-------------");
        return "about";
    }

    @GetMapping("/footer/newBlog")
    public String fragment(Model model){
        model.addAttribute("newBlogs",blogService.listTopBlog(3));
        return "fragments :: newBlogList";
    }

}
