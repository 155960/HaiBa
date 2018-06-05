package com.zengqiang.future.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    private static Logger logger= LoggerFactory.getLogger(PropertiesUtil.class);
    private static Properties props;

    static {
        String fileNname="ftp.properties";
        props=new Properties();
        try {
            props.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileNname));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("配置文件错误",e);
        }
    }

    public static Properties properties(String fileName){
        Properties properties=new Properties();
        try {
            properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("properties加载失败");
        }
        return properties;
    }

    public static String getProperty(String key){
        String value=props.getProperty(key.trim());
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key,String defaultValue){
        String value=props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            return defaultValue;
        }
        return value.trim();

    }
}
