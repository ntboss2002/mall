package com.zyxx.common.minio;

import com.zyxx.common.enums.StatusEnums;
import com.zyxx.common.utils.DateUtils;
import com.zyxx.common.utils.ResponseResult;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.messages.Bucket;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * minio工具类
 *
 * @Author zxy
 */
@Slf4j
@Component
public class MinioUtils {

    @Autowired
    private MinioClient client;
    @Autowired
    private MinioProp minioProp;

    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public void createBucket(String bucketName) {
        if (!client.bucketExists(bucketName)) {
            client.makeBucket(bucketName);
        }
    }

    /**
     * 获取全部bucket
     */
    @SneakyThrows
    public List<Bucket> getAllBuckets() {
        return client.listBuckets();
    }

    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public Optional<Bucket> getBucket(String bucketName) {
        return client.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 根据bucketName删除信息
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public void removeBucket(String bucketName) {
        client.removeBucket(bucketName);
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires    过期时间 <=7
     * @return url
     */
    @SneakyThrows
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        return client.presignedGetObject(bucketName, objectName, expires);
    }

    /**
     * 获取文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName) {
        return client.getObject(bucketName, objectName);
    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream     文件流
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream) throws Exception {
        client.putObject(bucketName, objectName, stream, stream.available(), "application/octet-stream");
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param size        大小
     * @param contextType 类型
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream, long size, String contextType) throws Exception {
        client.putObject(bucketName, objectName, stream, size, contextType);
    }

    /**
     * 获取文件信息
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#statObject
     */
    public ObjectStat getObjectInfo(String bucketName, String objectName) throws Exception {
        return client.statObject(bucketName, objectName);
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#removeObject
     */
    public void removeObject(String bucketName, String objectName) throws Exception {
        client.removeObject(bucketName, objectName);
    }

    /**
     * 上传文件
     *
     * @param file       文件
     * @param bucketName 存储桶
     * @return
     */
    public ResponseResult uploadFile(MultipartFile file, String bucketName, String dir) {
        // 判断上传文件是否为空
        if (null == file || 0 == file.getSize()) {
            return ResponseResult.error("上传文件不能为空");
        }
        if (StringUtils.isBlank(bucketName)) {
            bucketName = MinioConst.MINIO_BUCKET;
        }
        // 判断存储桶是否存在
        createBucket(bucketName);
        // 原始文件名
        String originalFilename = file.getOriginalFilename();
        // 文件后缀 例如：png
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        // uuid 生成文件名
        String uuid = String.valueOf(UUID.randomUUID());
        // 新的文件名
        String fileName = (StringUtils.isNotBlank(dir) ? dir + "/" : "") + DateUtils.getYyyymmdd() + "/" + uuid + "." + fileSuffix;
        try {
            // 如果是图片，且超过了 100K
            if (isPicture(fileSuffix) && (1024 * 1024 * 0.1) <= file.getSize()) {
                // 生成临时文件
                File newFile = new File(ClassUtils.getDefaultClassLoader().getResource("").getPath() + uuid + "." + fileSuffix);
                // 小于 1M
                if ((1024 * 1024 * 0.1) <= file.getSize() && file.getSize() <= (1024 * 1024)) {
                    Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.3f).toFile(newFile);
                }
                // 1M -- 2M
                else if ((1024 * 1024) < file.getSize() && file.getSize() <= (1024 * 1024 * 2)) {
                    Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.2f).toFile(newFile);
                }
                // 2M 以上的
                else if ((1024 * 1024 * 2) < file.getSize()) {
                    Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.1f).toFile(newFile);
                }
                FileInputStream input = new FileInputStream(newFile);
                file = new MockMultipartFile("file", newFile.getName(), "text/plain", input);
                // 删除临时文件
                newFile.delete();
            }
            // 开始上传
            client.putObject(bucketName, fileName, file.getInputStream(), file.getContentType());
            return ResponseResult.success(StatusEnums.UPLOAD_SUCCESS.getMsg(), minioProp.getEndpoint() + "/" + bucketName + "/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.error(StatusEnums.UPLOAD_ERROR);
    }

    /**
     * 判断文件是否为图片<br>
     */
    public boolean isPicture(String imgName) {
        if (StringUtils.isBlank(imgName)) {
            return false;
        }
        String[] arr = {"bmp", "dib", "gif", "jfif", "jpe", "jpeg", "jpg", "png", "tif", "tiff", "ico"};
        for (String item : arr) {
            if (item.equals(imgName)) {
                return true;
            }
        }
        return false;
    }
}
