package com.cgl.controller;

import com.cgl.model.ResponseResult;
import com.cgl.model.dto.CommentDTO;
import com.cgl.model.entity.Comment;
import com.cgl.service.CommentService;
import com.cgl.service.SqlGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;

@RestController
@RequestMapping("/community/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private SqlGenService sqlGenService;

    //    @GetMapping("/commentList")
//    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
//        return commentService.commentList(articleId,pageNum,pageSize);
//    }
    //一级评论
    @PostMapping("/commentRoot")
    public int CommentRoot(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {
        String UserId = sqlGenService.getIdByToken(request);
        System.out.println("----------"+commentDTO.getToCommentId());
        commentDTO.setCreateBy(Long.valueOf(UserId));
        Comment comment = new Comment();
        comment.setArticleId(commentDTO.getArticleId());
        comment.setRootId(commentDTO.getRootId());
        comment.setContent(commentDTO.getContent());
        comment.setToCommentUserId(commentDTO.getToCommentUserId()== null ? -1:commentDTO.getToCommentUserId());
        comment.setToCommentId(commentDTO.getToCommentId() == null ? -1 : commentDTO.getToCommentId());
        Date date = new Date(System.currentTimeMillis());//获得当前时间
        Timestamp t = new Timestamp(date.getTime());//将时间转换成Timestamp类型，这样便可以存入到Mysql数据库中
        comment.setCreateTime(t);
        int i = commentService.comment(comment);
        return i;
    }


    @GetMapping("/ShowComment")
    public ResponseResult GetAllComments(long articleId,Integer pageNum, Integer pageSize){

        ResponseResult responseResult = commentService.commentList(articleId, pageNum, pageSize);

        return responseResult;
    }

}
