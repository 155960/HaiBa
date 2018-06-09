package com.zengqiang.future.service.impl;

import com.google.common.collect.Lists;
import com.zengqiang.future.common.Const;
import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.dao.GoodMapper;
import com.zengqiang.future.pojo.Good;
import com.zengqiang.future.service.IFileService;
import com.zengqiang.future.util.FTPUtil;
import com.zengqiang.future.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

@Service
public class FileServiceImpl implements IFileService {

    /**
     *
     * @param file
     * @return  上传的静态资源访问路径
     */
    public String upload(MultipartFile file,int type){
        String path="";
        String fileName=file.getOriginalFilename();
        String fileExtensionName=fileName.substring(fileName.lastIndexOf(".")+1);
        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String remotePath= PropertiesUtil.getProperty("ftp.home");
        remotePath+="/"+path+"/"+month+"/"+day+"/";

        //加上前缀方便查找
        String uploadFileName=filePath(type)+System.currentTimeMillis()+"."+fileExtensionName;
        File dir=new File(path);
        if(!dir.exists()){
            dir.setWritable(true);
            dir.mkdirs();
        }
        File targetFile=new File(path,uploadFileName);
        try {
            file.transferTo(targetFile);
            FTPUtil.uploadFile(Lists.newArrayList(targetFile),remotePath);
            //访问路径
            String httpPath= PropertiesUtil.getProperty("http.path");
            httpPath+=remotePath;
            httpPath+=uploadFileName;
            return httpPath;
            //targetFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 约定
     * @param type
     * @return
     */
    private String filePath(int type){
        switch (type){
            case Const.Good.FILE:return "file";
            case Const.Good.IMG:return "img";
            case Const.Good.VIDEO:return "video";
        }
        return "";
    }
}
