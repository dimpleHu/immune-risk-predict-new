package com.immuserisk.controller;

import com.immuserisk.annotation.IgnoreAuth;
import com.immuserisk.common.R;
import com.immuserisk.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

//文件控制器
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    
    @Value("${server.port}")
    private String serverPort;
    
    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public R<String> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) String type,
            HttpServletRequest request) {
        try {
            String fileName = fileService.uploadFile(file, type);
            
            // 构建完整的文件访问URL
            String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + serverPort;
            String fileUrl = serverUrl + contextPath + "/files/" + fileName;
            
            return R.ok(fileUrl);
        } catch (Exception e) {
            return R.error("文件上传失败：" + e.getMessage());
        }
    }

    /**
     * 获取文件
     */
    @GetMapping("/files/{fileName}")
    @IgnoreAuth
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) {
        try {
            byte[] fileData = fileService.getFile(fileName);
            
            // 获取文件的MIME类型
            String contentType = "image/jpeg";
            if (fileName.toLowerCase().endsWith(".png")) {
                contentType = "image/png";
            } else if (fileName.toLowerCase().endsWith(".gif")) {
                contentType = "image/gif";
            }
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setCacheControl("max-age=31536000"); // 缓存一年
            
            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/{fileName}")
    public R<Void> delete(@PathVariable String fileName) {
        try {
            fileService.deleteFile(fileName);
            return R.ok();
        } catch (Exception e) {
            return R.error("文件删除失败：" + e.getMessage());
        }
    }
}
