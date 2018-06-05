package com.zengqiang.future.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class Bootstrap implements WebApplicationInitializer {


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext=
                new AnnotationConfigWebApplicationContext();
        rootContext.register(RootContextConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext springMvcContext=
                new AnnotationConfigWebApplicationContext();
        springMvcContext.register(SpringMvcConfiguration.class);
        ServletRegistration.Dynamic dynamic=servletContext.addServlet(
                "mvcservlet",new DispatcherServlet(springMvcContext));
        dynamic.setLoadOnStartup(1);
        dynamic.addMapping("/");
    }
}
