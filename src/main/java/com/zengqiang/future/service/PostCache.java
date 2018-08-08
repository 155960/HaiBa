package com.zengqiang.future.service;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.support.NameMatchMethodPointcut;

@Aspect
public class PostCache {

    public void test(){
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.addMethodName("save*");
        pointcut.addMethodName("delete*");
    }

}
