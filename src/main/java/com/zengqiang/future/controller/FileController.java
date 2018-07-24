package com.zengqiang.future.controller;

import com.zengqiang.future.common.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {

    @RequestMapping("upload")
    public ServerResponse upload(@RequestParam("files") MultipartFile[] files,int type){
        return null;
    }
}
