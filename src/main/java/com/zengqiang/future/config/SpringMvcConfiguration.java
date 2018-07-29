package com.zengqiang.future.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zengqiang.future.common.MyMultipartFileResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebMvc

@ComponentScan(
    basePackages = "com.zengqiang.future.controller"
)

public class SpringMvcConfiguration extends WebMvcConfigurerAdapter{

    @Inject
    ObjectMapper objectMapper;

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters
    ) {
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new FormHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<>());

        MappingJackson2HttpMessageConverter jsonConverter =
                new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "json"),
                new MediaType("text", "json")
        ));
        jsonConverter.setObjectMapper(this.objectMapper);
        converters.add(jsonConverter);
    }

    @Bean
    public MultipartResolver multipartResolver(){
        //servlet3.0上传
       // return new StandardServletMultipartResolver();
       return new MyMultipartFileResolver();
    }

   /* @Bean
    public PostCacheQueryIntercepter cacheIntercepter(){
        return new PostCacheQueryIntercepter();
    }

    @Bean
    public RegexpMethodPointcutAdvisor pointcutAdvisor(PostCacheQueryIntercepter intercepter){
        RegexpMethodPointcutAdvisor advisor=new RegexpMethodPointcutAdvisor();
        advisor.setPattern("save.*");
        advisor.setAdvice(intercepter);
        return advisor;
    }

    @Bean
    public ProxyFactoryBean factoryBean(TestService service){
        ProxyFactoryBean bean=new ProxyFactoryBean();
        bean.setTarget(service);
        bean.setInterceptorNames("pointcutAdvisor");
        return bean;
    }*/




}
