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
     *多個文件上傳
     * @param files
     * @param account
     * @return
     */
    public String[] upload(MultipartFile[] files,String account){
        File[] a=new File[files.length];
        String[] https=new String[files.length];//訪問路徑

        String remotePath= PropertiesUtil.getProperty("ftp.home");

        String httpPath= PropertiesUtil.getProperty("http.path");
        httpPath+=remotePath;

        remotePath+="/"+account+"/";
        for(int i=0;i<files.length;i++){
            String fileName=files[i].getOriginalFilename();
            File file=new File(remotePath);
            if(!file.exists()){
                file.setWritable(true);
                file.mkdirs();
            }
            a[i]=new File(remotePath,fileName);

            https[i]=httpPath+fileName;
            try {
                files[i].transferTo(a[i]);

            } catch (IOException e) {
                e.printStackTrace();
                https[i]=null;
                a[i]=null;
            }

        }

        boolean[] result=FTPUtil.uploadFile(Lists.newArrayList(a),remotePath);

        if(result[result.length-1]){
            return null;
        }
        for(int i=0;i<result.length-2;i++){
            if(result[i]){
                https[i]=null;
            }
        }
        return https;
    }
    /**
     *
     * @param file
     * @return  上传的静态资源访问路径
     */
    public String upload(MultipartFile file,String account){
        String[] result= upload(new MultipartFile[]{file},account);
        if(result==null){
            return null;
        }
        return result[0];
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
