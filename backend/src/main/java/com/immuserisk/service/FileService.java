package com.immuserisk.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务接口
 */
public interface FileService {
    /**
     * 上传文件
     * @param file 文件
     * @param type 文件类型（可选）
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file, String type);

    /**
     * 获取文件
     * @param fileName 文件名
     * @return 文件字节数组
     */
    byte[] getFile(String fileName);

    /**
     * 删除文件
     * @param fileName 文件名
     * @return 是否删除成功
     */
    boolean deleteFile(String fileName);
} 