package com.jmyz.controller;

import com.jmyz.entity.Files;
import com.jmyz.service.FileService;
import com.jmyz.utils.ResponseData;
import com.jmyz.utils.file.DownLoadUtil;
import com.jmyz.utils.file.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Api(value = "文件上传接口", tags = "文件上传接口需要token，查看图片不需token")
@Log4j2
@RestController
@RequestMapping("file")
public class FileController {

    @Resource
    private FileService fileService;
    /**
     * 上传
     */
    @ApiOperation(value = "上传", notes = "上传")
    @PostMapping(value = "upload")
    @RequiresRoles("admin")
    public ResponseData upload(Long fileIndex, @RequestParam("files") MultipartFile file, HttpServletRequest request) {
        Files files = FileUploadUtil.upFile(file,fileIndex,request);
        if(files.getFileId()==null){
            return new ResponseData<>(100, "上传失败");
        }
        if(fileService.saveFiles(files)!=1){
            return new ResponseData<>(100, "上传失败");
        }
        return new ResponseData<>(0, "上传成功",
                Objects.requireNonNull(files).getFileId());
    }

    @ApiOperation(value = "根据文件名查看图片", notes = "根据文件名查看图片")
    @GetMapping(value = "img/{fileName}")
    public void getImgByFileName(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        DownLoadUtil.downLoad(fileName, response);
    }

    @ApiOperation(value = "根据文件ID查看图片", notes = "根据文件ID查看图片")
    @GetMapping(value = "img/{fileId}")
    public void getImgByFileId(@PathVariable("fileId") String fileId, HttpServletResponse response) {
        DownLoadUtil.downLoad(fileService.getFileNameById(fileId), response);
    }
}
