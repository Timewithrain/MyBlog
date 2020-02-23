package com.watermelon.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandle(HttpServletRequest request,Exception e) throws Exception {
        //将错误的访问路径以及异常对象写入日志
        logger.error("Request URL : {}, Exception : {}",request.getRequestURL(),e);
        System.out.println("Exception!!!");
        //判断产生的一场是否为自定义异常，若是则直接抛出异常让springboot自动处理，否则跳转到error页面
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            throw e;
        }

        ModelAndView modelview = new ModelAndView();
        modelview.addObject("url",request.getRequestURL());
        modelview.addObject("exception",e);
        //产生异常以后的页面跳转路径
        modelview.setViewName("error/error");
        return modelview;
    }

}
