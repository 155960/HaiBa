package com.zengqiang.future.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zengqiang.future.util.PropertiesUtil;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(
        mode = AdviceMode.PROXY, proxyTargetClass = false
)
@PropertySource(value = {"classpath:datasource.properties"})
@ComponentScan(
        basePackages = "com.zengqiang.future.service"
)
public class RootContextConfiguration {

    @Bean
    public DataSource dataSource(@Value("${db.driverClassName}") String driverClass,
                                 @Value("${db.url}") String url,
                                 @Value("${db.username}") String username,
                                 @Value("${db.password}") String password,
                                 @Value("${c3p0.pool.maxPoolSize}") int maxSize,
                                 @Value("${c3p0.pool.minPoolSize}") int minSize,
                                 @Value("${c3p0.pool.initialPoolSize}") int initSize,
                                 @Value("${c3p0.pool.acquireIncrement}") int increment
                                 ) throws PropertyVetoException {
        ComboPooledDataSource dataSource=new ComboPooledDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(url);
        dataSource.setPassword(password);
        dataSource.setUser(username);
        dataSource.setMaxPoolSize(maxSize);
        dataSource.setMinPoolSize(minSize);
        dataSource.setInitialPoolSize(initSize);
        dataSource.setAcquireIncrement(increment);
        return dataSource;
    }

    @Bean
    public  PropertySourcesPlaceholderConfigurer propertyConfigInDev(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public PageInterceptor pageInterceptor(){
        PageInterceptor interceptor=new PageInterceptor();
        interceptor.setProperties(PropertiesUtil.properties("pagehelper.properties"));
        return interceptor;
    }

    @Bean
    public SqlSessionFactoryBean factoryBean(DataSource dataSource,Interceptor interceptor) throws IOException {
        SqlSessionFactoryBean factoryBean=new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml"));
        //配置PageHelper分页插件
        factoryBean.setPlugins(new Interceptor[]{interceptor});
        return factoryBean;
    }

    //扫描dao层mapper文件，自动生成dao层类
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer configurer=new MapperScannerConfigurer();
        configurer.setBasePackage("com.zengqiang.future.dao");
        return configurer;
    }

    @Bean
    public ObjectMapper objectMapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE,
                false);
        return mapper;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource source){
        DataSourceTransactionManager transactionManager=
                new DataSourceTransactionManager();
        transactionManager.setDataSource(source);
        transactionManager.setRollbackOnCommitFailure(true);
        return transactionManager;
    }


}
