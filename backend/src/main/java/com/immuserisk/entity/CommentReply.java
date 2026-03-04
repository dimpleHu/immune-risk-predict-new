package com.immuserisk.entity;

import lombok.Data;
import java.util.Date;

@Data
public class CommentReply {
    private Integer replyID;      // 回复编号
    private Integer commentID;    // 被回复的评论编号
    private String replierType;   // 回复人类型（user/admin）
    private Integer replierID;    // 回复人编号
    private String replyContent;  // 回复内容
    private Date replyTime;       // 回复时间
    
    // 非数据库字段
    private String replierName;   // 回复人名称
} 