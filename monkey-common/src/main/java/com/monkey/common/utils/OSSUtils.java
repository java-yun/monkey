package com.monkey.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Objects;

/**
 * 阿里云 oss
 * @author jiangyun
 * @version 0.0.1
 * @date 2021/1/4 16:01
 */
@Slf4j
public class OSSUtils {

    public static final String END_POINT;
    public static final String ACCESS_KEY_ID;
    public static final String ACCESS_KEY_SECRET;
    public static final String BUCKET_NAME;
    public static final String OSS_URL;

    public static JSONObject fileExtensionContentType;

    static{
        initFileExtensionContentType();
        END_POINT = PropertiesUtil.getProperty("endPoint");
        ACCESS_KEY_ID = PropertiesUtil.getProperty("accessKeyId");
        ACCESS_KEY_SECRET = PropertiesUtil.getProperty("accessKeySecret");
        BUCKET_NAME = PropertiesUtil.getProperty("bucketName");
        OSS_URL = PropertiesUtil.getProperty("ossUrl");
        log.info("======================oss config========================");
        log.info("endPoint:{}, accessKeyId:{}, accessKeySecret:{}, bucketName:{}, ossUrl:{}",END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET, BUCKET_NAME, OSS_URL);
    }

    private static void initFileExtensionContentType() {
        var inputStream = OSSUtils.class.getResourceAsStream("/fileExtensionContentType.json");
        try {
            var builder = new StringBuilder();
            byte [] b = new byte[1024];
            int len;
            while((len = inputStream.read(b)) != -1){
                builder.append(new String(b, 0, len));
            }
            fileExtensionContentType = JSONObject.parseObject(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static OSSClient initClient() {
        return new OSSClient(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 新建Bucket --Bucket权限:私有
     *
     * @param bucketName bucket名称
     */
    public static void createBucket(String bucketName) {
        OSSClient client = initClient();
        client.createBucket(bucketName);
        client.shutdown();
    }

    /**
     * 删除Bucket
     * @param bucketName bucket名称
     */
    public static void deleteBucket(String bucketName) {
        OSSClient client = initClient();
        client.deleteBucket(bucketName);
        client.shutdown();
    }

    /**
     * 向阿里云的OSS存储中存储文件 --file也可以用InputStream替代

     * @param file 上传文件
     * @param bucketName bucket名称
     * @param diskName 上传文件的目录 --bucket下文件的路径
     * @return true 成功
     */
    public static boolean upload(File file, String bucketName, String diskName){
        InputStream is = null;
        OSSClient client = initClient();
        try {
            is = new FileInputStream(file);
            String fileName = file.getName();
            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(is.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("UTF-8");
            metadata.setContentType(getContentType(fileName));
            // 上传文件
            client.putObject(bucketName, diskName + "/" + fileName, is, metadata);
        } catch (Exception e) {
            log.error("上传文件到OSS失败", e);
            return false;
        } finally {
            log.info("关闭文件的输入流！");
            try {
                if (Objects.nonNull(is)) {
                    is.close();
                }
                client.shutdown();
            } catch (Exception e) {
                log.error("关闭文件的输入流异常", e);
            }
        }
        return true;
    }

    /**
     * 向阿里云的OSS存储中存储文件
     *
     * @param is InputStream
     * @param bucketName bucket名称
     * @param diskName 上传文件的目录 --bucket下文件的路径
     * @return true 成功
     */
    public static boolean upload(InputStream is, String fileName, String bucketName, String diskName) {
        OSSClient client = initClient();
        try {
            log.info("upload start");
            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(is.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("UTF-8");
            metadata.setContentType(getContentType(fileName));
            log.info("uploading" + bucketName + diskName + fileName);
            // 上传文件
            PutObjectResult ddd = client.putObject(bucketName, diskName + "/" + fileName, is, metadata);
            log.info("uploaded");
            log.info("上传文件 result : " + JSONObject.toJSONString(ddd));
        } catch (Exception e) {
            log.error("上传文件到OSS失败", e);
            return false;
        } finally {
            if (null != is) {
                log.info("关闭文件的输入流！");
                try {
                    is.close();
                } catch (Exception e) {
                    log.error("关闭文件的输入流异常", e);
                }
                client.shutdown();
            }
        }
        return true;
    }

    /**
     * 根据key下载OSS服务器上的文件到本地
     *
     * @param bucketName bucket名称
     * @param yourLocalFile 文件路径
     * @param key Bucket下的文件的路径名+文件名
     */
    public static void downloadFile(String bucketName, String yourLocalFile, String key) {
        OSSClient client = initClient();
        client.getObject(new GetObjectRequest(bucketName, key), new File(yourLocalFile));
        client.shutdown();
    }

    /**
     * 根据key获取OSS服务器上的文件输入流
     *
     * @param bucketName bucket名称
     * @param diskName 文件路径
     * @param key Bucket下的文件的路径名+文件名
     * @return InputStream
     */
    public static InputStream getInputStream(String bucketName, String diskName, String key) {
        OSSClient client = initClient();
        OSSObject ossObj = client.getObject(bucketName, diskName + key);
        InputStream inputStream = ossObj.getObjectContent();
        client.shutdown();
        return inputStream;
    }

    /**
     * 根据key删除OSS服务器上的文件
     *
     * @param bucketName bucket名称
     * @param diskName 文件路径
     * @param key Bucket下的文件的路径名+文件名
     */
    public static void deleteFile(OSSClient client, String bucketName, String diskName, String key) {
        client.deleteObject(bucketName, diskName + key);
        log.info("删除" + bucketName + "下的文件" + diskName + key + "成功");
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static String getContentType(String fileName) {
        String contentType = "text/html";
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        contentType = fileExtensionContentType.getString(fileExtension);
        return contentType;
    }

    /**
     * 拼接完整的oss链接url
     * @param relativePath relativePath
     * @param ossUrl ossUrl
     * @return String
     */
    public static String packageOssStrUrl(String relativePath, String ossUrl){
        if(StringUtils.isEmpty(relativePath)){
            return "";
        }
        return ossUrl + relativePath;
    }

}
