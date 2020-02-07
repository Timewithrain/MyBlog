package com.watermelon.myblog;

import com.watermelon.entity.Tag;
import com.watermelon.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    public TagService tagService;

    @RequestMapping({"/tags.html","/tags"})
    public String tag(@PageableDefault(size=5,sort={"id"},direction=Sort.Direction.ASC) Pageable pageable, Model model){
        System.out.println("-----------tags-------------");
        model.addAttribute("page",tagService.listTag(pageable));
        return "/admin/tags";
    }

    @RequestMapping("/tags/add")
    public String add(Model model){
        model.addAttribute("tag",new Tag());
        return "/admin/tag-add";
    }

    @RequestMapping("/tags/doAdd")
    public String doAdd(Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1!=null){
            result.rejectValue("name","nameError","标签已存在");
        }
        if(result.hasErrors()){
            return "/admin/tag-add";
        }
        Tag t = tagService.saveTag(tag);
        if(t==null){
            attributes.addFlashAttribute("message","提交失败！");
        }else{
            attributes.addFlashAttribute("message","提交成功！");
        }
        return "redirect:/admin/tags.html";
    }

    @GetMapping("/tags/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "/admin/tag-add";
    }

    @PostMapping("/tags/{id}")
    public String doEdit(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1!=null){
            result.rejectValue("name","nameError","标签已存在");
        }
        if(result.hasErrors()){
            return "/admin/tag-add";
        }
        Tag t = tagService.updateTag(id,tag);
        if(t==null){
            attributes.addFlashAttribute("message","修改失败！");
        }else{
            attributes.addFlashAttribute("message","修改成功！");
        }
        return "redirect:/admin/tags.html";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","修改成功！");
        return "redirect:/admin/tags.html";
    }
}
