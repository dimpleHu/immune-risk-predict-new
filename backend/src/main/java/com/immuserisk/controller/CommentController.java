package com.immuserisk.controller;

import com.immuserisk.common.R;
import com.immuserisk.entity.Comment;
import com.immuserisk.entity.CommentReply;
import com.immuserisk.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     * @param comment 评论信息
     * @return 操作结果
     */
    @PostMapping("/add")
    public R<String> add(@RequestBody Comment comment) {
        log.info("添加评论：{}", comment);
        if (commentService.addComment(comment)) {
            return R.success("评论成功");
        }
        return R.error("评论失败");
    }

    /**
     * 获取用户订单评论列表
     * @param orderID 订单ID
     * @param userID 用户ID
     * @return 评论列表
     */
    @GetMapping("/getUserOrderByOrderIdAndUserId")
    public R<List<Comment>> getUserOrderComment(@RequestParam Integer orderID, @RequestParam Integer userID) {
        log.info("获取订单{}的用户{}评论", orderID, userID);
        List<Comment> comments = commentService.getUserOrderComment(orderID, userID);
        return R.success(comments);
    }

    /**
     * 获取评论列表
     * @param params 查询参数
     * @return 评论列表和总数
     */
    @GetMapping("/list")
    public R<Map<String, Object>> list(@RequestParam Map<String, Object> params) {
        log.info("获取评论列表：{}", params);
        try {
            Map<String, Object> result = commentService.getCommentList(params);
            return R.success(result);
        } catch (Exception e) {
            log.error("获取评论列表失败：", e);
            return R.error("获取评论列表失败：" + e.getMessage());
        }
    }

    /**
     * 添加评论回复
     * @param reply 回复信息
     * @return 操作结果
     */
    @PostMapping("/reply/add")
    public R<String> addReply(@RequestBody CommentReply reply) {
        log.info("添加评论回复：{}", reply);
        if (commentService.addCommentReply(reply)) {
            return R.success("回复成功");
        }
        return R.error("回复失败");
    }

    /**
     * 更新评论点赞数
     * @param commentID 评论ID
     * @return 操作结果
     */
    @PutMapping("/updateLikes")
    public R<String> updateLikes(@RequestParam Integer commentID) {
        log.info("更新评论{}的点赞数", commentID);
        if (commentService.updateLikes(commentID)) {
            return R.success("更新点赞数成功");
        }
        return R.error("更新点赞数失败");
    }

    /**
     * 获取项目评论列表
     * @param projectID 项目ID
     * @param page 页码
     * @param pageSize 每页大小
     * @return 评论列表和总数
     */
    @GetMapping("/projectcommentlist/{projectID}")
    public R<Map<String, Object>> getProjectCommentList(
            @PathVariable Integer projectID,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("获取项目{}的评论列表, 页码: {}, 每页大小: {}", projectID, page, pageSize);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("projectID", projectID);
            params.put("page", page);
            params.put("pageSize", pageSize);
            Map<String, Object> result = commentService.getProjectCommentList(projectID, page, pageSize);
            return R.success(result);
        } catch (Exception e) {
            log.error("获取项目评论列表失败：", e);
            return R.error("获取项目评论列表失败：" + e.getMessage());
        }
    }

    /**
     * 删除评论
     * @param commentID 评论ID (路径变量)
     */
    @DeleteMapping("/delete/{commentID}")
    public R<String> deleteComment(@PathVariable Integer commentID) {
        log.info("删除评论：{}", commentID);
        if (commentService.deleteComment(commentID)) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }
    /**
     * 删除评论回复
     * @param replyID 回复ID (路径变量)
     */
    @DeleteMapping("/reply/delete/{replyID}")
    public R<String> deleteCommentReply(@PathVariable Integer replyID) {
        log.info("删除回复：{}", replyID);
        if (commentService.deleteCommentReply(replyID)) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }
} 