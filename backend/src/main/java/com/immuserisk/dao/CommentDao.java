package com.immuserisk.dao;

import com.immuserisk.entity.Comment;
import com.immuserisk.entity.CommentReply;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentDao {
    /**
     * 获取评论列表
     */
    List<Comment> getCommentList(@Param("commentID") Integer commentID,
                                @Param("userID") Integer userID,
                                @Param("orderID") Integer orderID,
                                @Param("score") Integer score,
                                @Param("offset") Integer offset,
                                @Param("limit") Integer limit);

    /**
     * 获取评论总数
     */
    int getCommentCount(@Param("commentID") Integer commentID,
                       @Param("userID") Integer userID,
                       @Param("orderID") Integer orderID,
                       @Param("score") Integer score);

    /**
     * 获取评论回复列表
     */
    List<CommentReply> getCommentReplies(@Param("commentID") Integer commentID);

    /**
     * 添加评论回复
     */
    int addCommentReply(CommentReply reply);

    /**
     * 删除评论
     */
    int deleteComment(@Param("commentID") Integer commentID);

    /**
     * 删除评论回复
     */
    int deleteCommentReply(@Param("replyID") Integer replyID);

    /**
     * 更新点赞数
     */
    int updateLikes(@Param("commentID") Integer commentID);

    /**
     * 添加评论
     * @param comment 评论信息
     * @return 影响的行数
     */
    int insert(Comment comment);

    /**
     * 根据订单ID查询评论
     * @param orderID 订单ID
     * @return 评论信息
     */
    Comment getByOrderId(Integer orderID);

    /**
     * 添加评论
     * @param comment 评论信息
     * @return 影响行数
     */
    int addComment(Comment comment);

    /**
     * 获取用户订单评论列表
     * @param params 查询参数
     * @return 评论列表
     */
    List<Comment> getUserOrderComment(Map<String, Object> params);

    /**
     * 获取评论列表
     * @param params 查询参数
     * @return 评论列表
     */
    List<Comment> getCommentList(Map<String, Object> params);

    /**
     * 获取评论总数
     * @param params 查询参数
     * @return 评论总数
     */
    int getCommentCount(Map<String, Object> params);

    /**
     * 获取项目评论列表
     * @param params 查询参数
     * @return 评论列表
     */
    List<Comment> getProjectCommentList(Map<String, Object> params);

    /**
     * 获取项目评论总数
     * @param params 查询参数
     * @return 评论总数
     */
    int getProjectCommentCount(Map<String, Object> params);
} 