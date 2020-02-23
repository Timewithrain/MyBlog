package com.watermelon.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //面向切面，将com.watermelon.myblog包下的所有类的所有方法进行拦截,发起请求后将执行log()方法
    @Pointcut("execution(* com.watermelon.myblog.*.*(..))")
    public void log(){}

    //在log()方法执行之前执行doBefore()方法
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        //拦截请求，获取请求的url、ip、方法名、参数等信息写入日志文件
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteAddr();
        String url = request.getRequestURL().toString();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+ "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(ip,url,classMethod,args);
        logger.info("request : {}",requestLog);
    }

    //在log()方法执行之后执行doAfter()方法
    @After("log()")
    public void doAfter(){
        logger.info("------------doAfter--------------");
    }

    //捕获后台功能方法执行完毕以后的返回结果，将返回结果以result对象的方式传入方法
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        //将拦截的返回结果写入日志文件
        logger.info("result : {}",result);
    }


}
