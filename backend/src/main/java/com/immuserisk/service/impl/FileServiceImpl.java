package com.immuserisk.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.immuserisk.common.exception.BusinessException;
import com.immuserisk.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 文件服务实现类
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path uploadPath;

    @PostConstruct
    public void init() {
        String currentPath = System.getProperty("user.dir");
        this.uploadPath = Paths.get(currentPath).resolve(uploadDir);
        FileUtil.mkdir(this.uploadPath.toString());
    }

    @Override
    public String uploadFile(MultipartFile file, String type) {
        if (file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }

        // 获取文件扩展名
        String originalFilename = file.getOriginalFilename();
        String fileExt = StrUtil.subAfter(originalFilename, ".", true);
        
        // 生成新的文件名
        String fileName = UUID.randomUUID().toString() + "." + fileExt;
        File dest = uploadPath.resolve(fileName).toFile();
        
        try {
            // 保存文件
            file.transferTo(dest);
            log.info("文件上传成功：{}", fileName);
            return fileName;
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException("文件上传失败：" + e.getMessage());
        }
    }

    @Override
    public byte[] getFile(String fileName) {
        File file = uploadPath.resolve(fileName).toFile();
        if (!file.exists()) {
            throw new BusinessException("文件不存在");
        }
        return FileUtil.readBytes(file);
    }

    @Override
    public boolean deleteFile(String fileName) {
        File file = uploadPath.resolve(fileName).toFile();
        if (file.exists()) {
            return FileUtil.del(file);
        }
        return true;
    }
} 