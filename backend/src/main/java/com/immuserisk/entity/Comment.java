package com.immuserisk.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Comment {
    private Integer commentID;    // 评论编号
    private Integer userID;       // 评论用户编号
    private Integer orderID;      // 评论订单编号
    private Integer projectID;    // 项目编号
    private String comments;      // 评论文字内容
    private String commentImage;  // 评论图片链接或路径
    private Integer score;        // 评分（1-5分）
    private LocalDateTime commentTime;     // 评论时间
    private Integer likes;        // 点赞数
    
    // 非数据库字段
    private String userName;      // 评论用户名称
    private String projectName;   // 项目名称
    private List<CommentReply> replies; // 评论回复列表
} 