package com.cgl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cgl.model.ResponseResult;
import com.cgl.model.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-03-27 21:53:38
 */
public interface CommentService extends IService<Comment> {
//    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);

    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);

    //写评论
    public int comment(Comment Comment);
    public int getCommentNumByDate(String date,int userid);
}

