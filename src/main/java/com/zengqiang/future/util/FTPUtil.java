package com.zengqiang.future.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

public class FTPUtil {
    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);
    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private static String ftpPass = PropertiesUtil.getProperty("ftp.pass");
    private static String rootDir = PropertiesUtil.getProperty("ftp.home");

    private FTPClient client;
    private String user;
    private String pwd;
    private String ip;
    private int port;

    public FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
        client = new FTPClient();
    }

    //创建文件夹是遇到一个坑，linux下不能创建多级目录，只能一级一级创建
    private void createDir(String path) throws IOException {
        client.changeWorkingDirectory(rootDir);
        //默认已存在文件路径
        String[] a = rootDir.split("/");
        String[] dirs = path.split("/");
        //忽略已有的根目錄
        int current = a.length;
        for (int i = current; i < dirs.length; i++) {
            //文件存在则返回false
            client.makeDirectory(dirs[i]);
            //cd到当前目录
            client.changeWorkingDirectory(dirs[i]);
        }

    }

    public static boolean[] uploadFile(List<File> fileList, String remotePath) {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
        //logger.info("开始连接服务器");
        boolean[] result = ftpUtil.uploadFile(remotePath, fileList);
        return result;
    }

    public static boolean downloadFile(List<File> fileList) {
        FTPUtil ftpUtil=new FTPUtil(ftpIp, 21, ftpUser, ftpPass);

        return false;
    }

    private boolean downloadFile(String path, String remotePath, List<File> fileList) throws IOException {
       /* boolean flag = false;
        OutputStream os=null;
        try {
            System.out.println("开始下载文件");
            initFtpClient();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for(FTPFile file : ftpFiles){
                if(filename.equalsIgnoreCase(file.getName())){
                    File localFile = new File(localpath + "/" + file.getName());
                    os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftpClient.logout();
            flag = true;
            System.out.println("下载文件成功");
        } catch (Exception e) {
            System.out.println("下载文件失败");
            e.printStackTrace();
        } finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
        return false;
    }

    /**
     * @param remotePath linux下文件存储路径如 /home/ftp/img
     * @param fileList   要存的文件集合
     * @return
     * @throws IOException
     */
    private boolean[] uploadFile(String remotePath, List<File> fileList) {
        /*upload每一位機載是否上傳成功，最後一位記錄上傳是否失敗*/
        boolean[] upload = new boolean[fileList.size() + 1];
        FileInputStream fis = null;
        try {
            if (connectServer(this.ip, this.port, this.user, this.pwd)) {
                //创建路径
                try {
                    createDir(remotePath);
                    //client.changeWorkingDirectory(remotePath);
                    client.setBufferSize(1024);
                    client.setDataTimeout(3000);
                    client.setConnectTimeout(3000);
                    client.setControlEncoding("UTF-8");
                    client.setFileType(FTPClient.BINARY_FILE_TYPE);
                    //少了这句将无法创建文件
                    client.enterLocalPassiveMode();
                    System.out.println("创建文件");
                } catch (IOException e) {
                    e.printStackTrace();
                    upload[fileList.size()] = true;
                }
                System.out.println("**"+fileList.size());
                for (int i=0;i<fileList.size();i++) {
                    File fileItem=fileList.get(i);
                    try {
                        System.out.println("准备上传");
                        if (fileItem != null) {
                            fis = new FileInputStream(fileItem);
                            System.out.println("存储文件中");
                            client.storeFile(fileItem.getName(), fis);
                            System.out.println("上传结束");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(fileItem.getName()+"上傳失敗");
                        upload[i] = true;
                    }
                }
            }
        } finally {
            try {
                if(fis!=null)
                    fis.close();
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return upload;
    }



    //连接ftp服务器
    private boolean connectServer(String ip, int port, String user, String pwd) {
        boolean isSuccess = false;
        try {
            //默认端口21
            client.connect(ip);
            // PrintUtil.print(client.getReply()+"**");
            isSuccess = client.login(user, pwd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
