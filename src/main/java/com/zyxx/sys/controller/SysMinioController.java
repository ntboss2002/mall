package com.zyxx.sys.controller;

import com.zyxx.common.minio.MinioConst;
import com.zyxx.common.minio.MinioUtils;
import com.zyxx.common.utils.ResponseResult;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zxy
 * @since 2020-07-15
 **/
@Api(tags = "后台管理端--文件上传")
@Controller
@RequestMapping("/sys/sys-minio")
public class SysMinioController {

    @Autowired
    private MinioUtils minioUtils;

    @GetMapping("init")
    public String init() {
        return "sys/minio/upload";
    }

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return
     */
    @PostMapping("uploadFile")
    @ResponseBody
    public ResponseResult uploadFile(@RequestParam(name = "file") MultipartFile file, @RequestParam(value = "folder", required = false, defaultValue = "default") String folder) {
        return minioUtils.uploadFile(file, "", folder);
    }

    /**
     * 上传文件
     *
     * @return
     */
    @PostMapping("uploadFileAjax")
    @ResponseBody
    public ResponseResult uploadFileAjax(HttpServletRequest request) {
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multiRequest.getFile("files");
        String dir = request.getHeader("Minio-dir");
        return minioUtils.uploadFile(file, "", dir);
    }

    /**
     * 下载文件
     *
     * @param fileUrl  文件路径
     * @param response
     * @throws IOException
     */
    @GetMapping("downloadFile")
    public void downloadFile(String fileUrl, HttpServletResponse response) throws IOException {
        if (StringUtils.isBlank(fileUrl)) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            String data = "文件下载失败";
            OutputStream ps = response.getOutputStream();
            ps.write(data.getBytes("UTF-8"));
            return;
        }
        try {
            // 拿到文件路径
            String url = fileUrl.split("9000/")[1];
            // 获取文件对象
            InputStream object = minioUtils.getObject(MinioConst.MINIO_BUCKET, url.substring(url.indexOf("/") + 1));
            byte buf[] = new byte[1024];
            int length = 0;
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(url.substring(url.lastIndexOf("/") + 1), "UTF-8"));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            OutputStream outputStream = response.getOutputStream();
            // 输出文件
            while ((length = object.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
            // 关闭输出流
            outputStream.close();
        } catch (Exception ex) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            String data = "文件下载失败";
            OutputStream ps = response.getOutputStream();
            ps.write(data.getBytes("UTF-8"));
        }
    }
}
