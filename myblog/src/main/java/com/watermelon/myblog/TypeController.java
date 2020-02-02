package com.watermelon.myblog;

import com.watermelon.entity.Type;
import com.watermelon.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @RequestMapping({"/types.html","/types"})
    public String types(@PageableDefault(size=5,sort={"id"},direction=Sort.Direction.ASC) Pageable pageable,Model model){
        System.out.println("-----------types-------------");
        model.addAttribute("page",typeService.listType(pageable));
        return "/admin/types";
    }

    @RequestMapping("/types/add")
    public String add(Model model){
        model.addAttribute("type",new Type());
        return "/admin/type-add";
    }

    @PostMapping("/types/doAdd")
    public String doAdd(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        //通过Type名称查找输入分类是否重复，若重复返回错误信息及提示
        Type t0 = typeService.getTypeByName(type.getName());
        if(t0!=null){
            result.rejectValue("name","nameError","分类已存在");
        }
        //根据Type类中@NotBlanck注解对从前输入的对象进行校验，若校验失败则获取错误message重定向回type-add页面
        if(result.hasErrors()){
            return "redirect:/admin/types/add";
        }
        Type t = typeService.saveType(type);
        if(t==null){
            attributes.addFlashAttribute("message","提交失败！");
        }else{
            attributes.addFlashAttribute("message","提交成功！");
        }
        return "redirect:/admin/types.html";
    }

    @PostMapping("/types/delete")
    public String delete(Long id){
        typeService.deleteType(id);
        return "redirect:/admin/types.html";
    }

}
