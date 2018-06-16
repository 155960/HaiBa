package com.zengqiang.future.common;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MyMultipartFileResolver extends CommonsMultipartResolver {
    @Override
    protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {

        String encoding = this.determineEncoding(request);
        //调用自己实现的
        FileUpload fileUpload = this.prepareFileUpload(encoding,request);

        try {
            List<FileItem> fileItems = ((ServletFileUpload)fileUpload).parseRequest(request);
            return this.parseFileItems(fileItems, encoding);
        } catch (FileUploadBase.SizeLimitExceededException var5) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), var5);
        } catch (FileUploadException var6) {
            throw new MultipartException("Could not parse multipart servlet request", var6);
        }
    }

    protected FileUpload prepareFileUpload(String encoding,HttpServletRequest request) {
        FileUpload fileUpload = getFileUpload();
        FileUpload actualFileUpload = fileUpload;
        // Use new temporary FileUpload instance if the request specifies
        // its own encoding that does not match the default encoding.
        if (encoding != null && !encoding.equals(fileUpload.getHeaderEncoding())) {
            actualFileUpload = newFileUpload(getFileItemFactory());
            actualFileUpload.setHeaderEncoding(encoding);
            System.out.println(request.getRequestURL()+"&&"+request.getRequestURI());
            boolean f=request.getRequestURI().contains("/file/upload");
            String type=request.getParameter("type");
            if(f&&type!=null){
                int t=Integer.parseInt(type);
                switch (t){
                    //重新设置文件限制5M
                    case Const.Good.IMG:actualFileUpload.setSizeMax(512 * 1024 * 10);break;
                    //重新设置文件限制100M
                    case Const.Good.FILE:actualFileUpload.setSizeMax(1024 * 1024 * 100);break;
                    //重新设置文件限制2000M
                    case Const.Good.VIDEO:actualFileUpload.setSizeMax(1024 * 1024 * 2000);break;
                    default:actualFileUpload.setSizeMax(fileUpload.getSizeMax());
                }
            }
        }
        return actualFileUpload;
    }


}
