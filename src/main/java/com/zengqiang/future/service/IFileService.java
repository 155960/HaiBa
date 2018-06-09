package com.zengqiang.future.service;

import com.zengqiang.future.common.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String upload(MultipartFile file,int type);
}
