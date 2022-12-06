package com.zyxx.api.controller;

import com.zyxx.common.minio.MinioUtils;
import com.zyxx.common.utils.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "文件上传")
@RestController
@RequestMapping("/api/upload")
public class UploadApiController {

    @Autowired
    private MinioUtils minioUtils;

    @PostMapping(value = "upload", headers = "content-type=multipart/form-data")
    public ResponseResult upload(HttpServletRequest request) {
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multiRequest.getFile("file");
        return minioUtils.uploadFile(file, "", "app");
    }

}
