package com.immuserisk.service.impl;

import com.immuserisk.dao.CommentDao;
import com.immuserisk.entity.Comment;
import com.immuserisk.entity.CommentReply;
import com.immuserisk.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    public Map<String, Object> getCommentList(Map<String, Object> params) {
        // 设置默认值
        int page = params.get("page") != null ? Integer.parseInt(params.get("page").toString()) : 1;
        int limit = params.get("limit") != null ? Integer.parseInt(params.get("limit").toString()) : 10;
        params.put("offset", (page - 1) * limit);
        params.put("limit", limit);

        // 获取评论列表和总数
        List<Comment> commentList = commentDao.getCommentList(params);
        int total = commentDao.getCommentCount(params);

        Map<String, Object> result = new HashMap<>();
        result.put("records", commentList);
        result.put("total", total);
        return result;
    }

    @Override
    @Transactional
    public boolean addCommentReply(CommentReply reply) {
        reply.setReplyTime(new Date());  // 设置回复时间为当前时间
        return commentDao.addCommentReply(reply) > 0;
    }

    @Override
    @Transactional
    public boolean deleteComment(Integer commentID) {
        return commentDao.deleteComment(commentID) > 0;
    }

    @Override
    @Transactional
    public boolean deleteCommentReply(Integer replyID) {
        return commentDao.deleteCommentReply(replyID) > 0;
    }

    @Override
    @Transactional
    public boolean updateLikes(Integer commentID) {
        return commentDao.updateLikes(commentID) > 0;
    }

    @Override
    public boolean addComment(Comment comment) {
        return commentDao.addComment(comment) > 0;
    }

    @Override
    public Comment getOrderComment(Integer orderID) {
        return commentDao.getByOrderId(orderID);
    }

    @Override
    public List<Comment> getUserOrderComment(Integer orderID, Integer userID) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderID", orderID);
        params.put("userID", userID);
        return commentDao.getUserOrderComment(params);
    }

    @Override
    public Map<String, Object> getProjectCommentList(Integer projectID, Integer page, Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("projectID", projectID);
        params.put("offset", (page - 1) * pageSize);
        params.put("limit", pageSize);

        // 获取评论列表和总数
        List<Comment> commentList = commentDao.getProjectCommentList(params);
        int total = commentDao.getProjectCommentCount(params);

        Map<String, Object> result = new HashMap<>();
        result.put("records", commentList);
        result.put("total", total);
        return result;
    }
} 