package com.immuserisk.service;

import com.immuserisk.entity.Comment;
import com.immuserisk.entity.CommentReply;
import java.util.List;
import java.util.Map;

public interface CommentService {
    /**
     * 获取评论列表
     */
    Map<String, Object> getCommentList(Map<String, Object> params);

    /**
     * 添加评论回复
     * @param reply 回复信息
     * @return 是否成功
     */
    boolean addCommentReply(CommentReply reply);

    /**
     * 删除评论
     */
    boolean deleteComment(Integer commentID);

    /**
     * 删除评论回复
     */
    boolean deleteCommentReply(Integer replyID);

    /**
     * 更新点赞数
     */
    boolean updateLikes(Integer commentID);

    /**
     * 添加评论
     * @param comment 评论信息
     * @return 是否成功
     */
    boolean addComment(Comment comment);

    /**
     * 获取订单评论
     * @param orderID 订单ID
     * @return 评论信息
     */
    Comment getOrderComment(Integer orderID);

    /**
     * 获取用户订单评论列表
     * @param orderID 订单ID
     * @param userID 用户ID
     * @return 评论列表
     */
    List<Comment> getUserOrderComment(Integer orderID, Integer userID);

    /**
     * 获取项目评论列表
     * @param projectID 项目ID
     * @param page 页码
     * @param pageSize 每页大小
     * @return 评论列表和总数
     */
    Map<String, Object> getProjectCommentList(Integer projectID, Integer page, Integer pageSize);
} 