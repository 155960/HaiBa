package com.zengqiang.future.controller;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.pojo.Good;
import com.zengqiang.future.service.IFileService;
import com.zengqiang.future.service.IGoodService;
import com.zengqiang.future.util.TokenUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/file")
@ResponseBody
public class FileController {

    private static final String PATH="/home/ftpuser/";
    private static ConcurrentHashMap<String,ProcessInfo>  process=
            new ConcurrentHashMap<>();

    @Autowired
    IFileService fileService;

    @Autowired
    IGoodService goodService;


    /**
     * 新建post时，post记录插入成功后，转发到此，附带postid
     * @param request
     * @return
     */
    @RequestMapping(value = "upload" ,method = RequestMethod.POST)
    public ServerResponse upload(HttpServletRequest request,int postId){
        //是否有MultipartFile
        boolean isMultipart=ServletFileUpload.isMultipartContent(request);
        if(!isMultipart){
            return ServerResponse.createBySuccessMessage("没有文件");
        }
        FileItemFactory fileItemFactory=new DiskFileItemFactory();
        //String token=request.getHeader("Authorization");
        String account= "18392867649";//TokenUtil.getAccount(token);
        ServletFileUpload upload=new ServletFileUpload(fileItemFactory);
        //避免上传 后的文件乱码
        upload.setHeaderEncoding("UTF-8");
        List<FileItem> items= null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        upload.setProgressListener(new ProgressListener(){
            /**
             *
             * @param pBytesRead 已上传数量
             * @param pContentLength 总长度
             * @param pItems  第几个文件
             */
            public void update(long pBytesRead, long pContentLength, int pItems) {
                ProcessInfo pri = new ProcessInfo();
                pri.itemNum = pItems;
                pri.readSize = pBytesRead;
                pri.totalSize = pContentLength;
                pri.show = pBytesRead+"/"+pContentLength+" byte";
                pri.rate = Math.round(new Float(pBytesRead) / new Float(pContentLength)*100);

            }
        });

        File file=new File(PATH+account);
        if(!file.exists()){
            file.setWritable(true);
            file.mkdirs();
        }
        List<Good> goods=new ArrayList<>();
        for(FileItem fileItem:items){
            if(!fileItem.isFormField()){
                System.out.println("上传");
                int index=fileItem.getName().lastIndexOf(".");
                String dispiay_name;
                String suffix="";
                //没有后缀时
                if(index==-1){
                    dispiay_name=fileItem.getName();
                }else{
                   dispiay_name=fileItem.getName().substring(0,index);
                   suffix=fileItem.getName().substring(index+1);
                }
                String newName=account+"-"+System.currentTimeMillis()+"."+suffix;
                File f=new File(PATH+account+"/",newName);
                try {
                    fileItem.write(f);

                    //上传成功向数据库插入数据
                    Good good=goodService.createGood(newName,dispiay_name,account,postId);
                    goods.add(good);
                } catch (Exception e) {
                    e.printStackTrace();
                    //插入或者上传发生错误删除上传文件
                    f.delete();
                }
            }
        }
        return ServerResponse.createBySuccess(goods);
    }

    /**
     * process 获取进度
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/process", method = RequestMethod.GET)
    @ResponseBody
    public Object process(HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        return ( ProcessInfo)request.getSession().getAttribute("proInfo");
    }


    /**
     * 下载文件返回一个URL，客户端通过url下载
     * @param id
     * @return
     */
    @RequestMapping(value = "download",method = RequestMethod.POST)
    public ServerResponse download(int id){

        return null;
    }

    class ProcessInfo{
        public long totalSize = 1;
        public long readSize = 0;
        public String show = "";
        public int itemNum = 0;
        public int rate = 0;
    }

}
