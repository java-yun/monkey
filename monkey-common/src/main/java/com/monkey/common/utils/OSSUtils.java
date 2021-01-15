package com.monkey.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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

    static{
        END_POINT = PropertiesUtil.getProperty("endPoint");
        ACCESS_KEY_ID = PropertiesUtil.getProperty("accessKeyId");
        ACCESS_KEY_SECRET = PropertiesUtil.getProperty("accessKeySecret");
        BUCKET_NAME = PropertiesUtil.getProperty("bucketName");
        OSS_URL = PropertiesUtil.getProperty("ossUrl");
        log.info("======================oss config========================");
        log.info("endPoint:{}, accessKeyId:{}, accessKeySecret:{}, bucketName:{}, ossUrl:{}",END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET, BUCKET_NAME, OSS_URL);
    }

    public static OSSClient initClient() {
        return new OSSClient(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 新建Bucket --Bucket权限:私有
     *
     * @param bucketName
     *            bucket名称
     * @return true 新建Bucket成功
     */
    public static boolean createBucket(OSSClient client, String bucketName) {
        Bucket bucket = client.createBucket(bucketName);
        return bucketName.equals(bucket.getName());
    }

    /**
     * 删除Bucket
     *
     * @param bucketName
     *            bucket名称
     */
    public static void deleteBucket(OSSClient client, String bucketName) {
        client.deleteBucket(bucketName);
        log.info("删除" + bucketName + "Bucket成功");
    }

    /**
     * 向阿里云的OSS存储中存储文件 --file也可以用InputStream替代
     *
     * @param client
     *            OSS客户端
     * @param file
     *            上传文件
     * @param bucketName
     *            bucket名称
     * @param diskName
     *            上传文件的目录 --bucket下文件的路径
     * @return String 唯一MD5数字签名
     */
    public static boolean uploadObject2OSS(OSSClient client, File file, String bucketName, String diskName) throws FileNotFoundException {
        InputStream is = new FileInputStream(file);
        try {
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
                is.close();
            } catch (Exception e) {
                log.error("关闭文件的输入流异常", e);
            }
        }
        return true;
    }

    /**
     * 向阿里云的OSS存储中存储文件 --file也可以用InputStream替代
     *
     * @param client
     *            OSS客户端
     * @param is
     *            上传文件
     * @param bucketName
     *            bucket名称
     * @param diskName
     *            上传文件的目录 --bucket下文件的路径
     * @return String 唯一MD5数字签名
     */
    public static boolean uploadInputStreamObject2OSS(OSSClient client, InputStream is, String fileName, String bucketName, String diskName) throws FileNotFoundException {
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
     * 根据key获取OSS服务器上的文件到本地
     *
     * @param client
     *            OSS客户端
     * @param bucketName
     *            bucket名称
     * @param yourLocalFile
     *            文件路径
     * @param key
     *            Bucket下的文件的路径名+文件名
     */
    public static void getOSS2LocalFile(OSSClient client, String bucketName, String yourLocalFile, String key) {
        client.getObject(new GetObjectRequest(bucketName, key), new File(yourLocalFile));
    }

    /**
     * 根据key获取OSS服务器上的文件输入流
     *
     * @param client
     *            OSS客户端
     * @param bucketName
     *            bucket名称
     * @param diskName
     *            文件路径
     * @param key
     *            Bucket下的文件的路径名+文件名
     */
    public static InputStream getOSS2InputStream(OSSClient client, String bucketName, String diskName, String key) {
        OSSObject ossObj = client.getObject(bucketName, diskName + key);
        return ossObj.getObjectContent();
    }

    /**
     * 根据key删除OSS服务器上的文件
     *
     * @param client
     *            OSS客户端
     * @param bucketName
     *            bucket名称
     * @param diskName
     *            文件路径
     * @param key
     *            Bucket下的文件的路径名+文件名
     */
    public static void deleteFile(OSSClient client, String bucketName, String diskName, String key) {
        client.deleteObject(bucketName, diskName + key);
        log.info("删除" + bucketName + "下的文件" + diskName + key + "成功");
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName
     *            文件名
     * @return 文件的contentType
     */
    public static String getContentType(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if ("bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if ("gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if ("jpeg".equalsIgnoreCase(fileExtension) || "jpg".equalsIgnoreCase(fileExtension) || "png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if ("html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if ("txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if ("vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if ("ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if ("doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if ("xls".equalsIgnoreCase(fileExtension) ) {
            return "application/vnd.ms-excel";
        }
        if ("xlsx".equalsIgnoreCase(fileExtension) ) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        if ("xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        if ("mp4".equalsIgnoreCase(fileExtension)) {
            return "video/mp4";
        }
        if ("flv".equalsIgnoreCase(fileExtension)) {
            return "video/x-flv";
        }
        if ("swf".equalsIgnoreCase(fileExtension)) {
            return "application/x-shockwave-flash";
        }
        if ("mkv".equalsIgnoreCase(fileExtension)) {
            return "video/x-matroska";
        }
        if ("avi".equalsIgnoreCase(fileExtension)) {
            return "video/x-msvideo";
        }
        if ("rm".equalsIgnoreCase(fileExtension) || "rmvb".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.rn-realmedia";
        }
        if ("mpeg".equalsIgnoreCase(fileExtension) || "mpg".equalsIgnoreCase(fileExtension)) {
            return "video/mpeg";
        }
        if ("ogg".equalsIgnoreCase(fileExtension)) {
            return "video/x-theora+ogg";
        }
        if ("ogv".equalsIgnoreCase(fileExtension)) {
            return "video/ogg";
        }
        if ("mov".equalsIgnoreCase(fileExtension)) {
            return "video/quicktime";
        }
        if ("wmv".equalsIgnoreCase(fileExtension)) {
            return "video/x-ms-wmv";
        }
        if ("webm".equalsIgnoreCase(fileExtension)) {
            return "video/webm";
        }
        if ("mp3".equalsIgnoreCase(fileExtension)) {
            return "audio/mpeg";
        }
        if ("wav".equalsIgnoreCase(fileExtension)) {
            return "audio/x-wav";
        }
        if ("mid".equalsIgnoreCase(fileExtension)) {
            return "audio/midi";
        }
        if ("apk".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.android.package-archive";
        }
        return "text/html";
    }

    /**
     * 拼接完整的oss链接url
     * @param relativePath
     * @param ossUrl
     * @return
     */
    public static String packageOssStrUrl(String relativePath, String ossUrl){
        if(StringUtils.isEmpty(relativePath)){
            return "";
        }
        return ossUrl + relativePath;
    }
}
