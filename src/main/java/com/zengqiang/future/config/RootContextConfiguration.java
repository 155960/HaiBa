package com.zengqiang.future.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zengqiang.future.common.Const;
import com.zengqiang.future.common.MyRedisCacheManager;
import com.zengqiang.future.util.PropertiesUtil;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(
        mode = AdviceMode.PROXY, proxyTargetClass = false
)
@PropertySource(value = {"classpath:datasource.properties","classpath:redis.properties"})
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

    /* <bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig">
        <!-- 最大空闲数 -->
        <property name="maxIdle" value="${redis.maxIdle}" />
        <!-- 最大空连接数 -->
        <property name="maxTotal" value="${redis.maxTotal}" />
        <!-- 最大等待时间 -->
        <property name="maxWaitMillis" value="${redis.maxWaitMillis}" />
        <!-- 连接超时时是否阻塞，false时报异常,ture阻塞直到超时, 默认true -->
         <property name="blockWhenExhausted" value="${redis.blockWhenExhausted}" />
        <!-- 返回连接时，检测连接是否成功 -->
        <property name="testOnBorrow" value="${redis.testOnBorrow}" />
    </bean>*/
    @Bean
    public JedisPoolConfig jedisPoolConfig(@Value("redis.maxIdle")int maxIdle,
                                           @Value("redis.minEvictableIdleTimeMillis")int minEvi,
                                           @Value("redis.numTestsPerEvictionRun")int numTest,
                                           @Value("redis.timeBetweenEvictionRunsMillis")int timeBet){
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMinEvictableIdleTimeMillis(minEvi);
        config.setNumTestsPerEvictionRun(numTest);
        config.setTimeBetweenEvictionRunsMillis(timeBet);
        return config;
    }
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig config,
                                                         @Value("redis.hostName")String hostName,
                                                         @Value("redis.port")int port,
                                                         @Value("redis.timeout")int timeout,
                                                         @Value("redis.usePool")boolean usePool){
        JedisConnectionFactory factory=new JedisConnectionFactory();
        factory.setHostName(hostName);
        factory.setPort(port);
        factory.setTimeout(timeout);
        factory.setUsePool(usePool);
        factory.setPoolConfig(config);
        return factory;
    }

    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory factory){
        RedisTemplate template=new RedisTemplate();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());//序列化方法
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        return template;
    }

    //缓存管理配置，主要配置过期时间
    @Bean
    public CacheManager cacheManager(RedisTemplate template){
        MyRedisCacheManager manager=new MyRedisCacheManager(template);
        Map<String ,Long> expires=new HashMap<>();
        //各类缓存失效时间配置
        expires.put(Const.CachePrefix.HOTPOST,86400L);//一天
        manager.setExpires(expires);
        return manager;
    }
}
