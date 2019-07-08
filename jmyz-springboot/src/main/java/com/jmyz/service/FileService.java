package com.jmyz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jmyz.entity.Files;


public interface FileService extends IService<Files> {

    int saveFiles(Files filesList);

    String getFileNameById(String fileId);
}
