package com.cgl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgl.mapper.CommentMapper;
import com.cgl.mapper.UserMapper;
import com.cgl.model.ResponseResult;
import com.cgl.model.entity.Comment;
import com.cgl.model.entity.User;
import com.cgl.model.vo.CommentPageVo;
import com.cgl.model.vo.CommentVo;
import com.cgl.service.SqlGenService;
import com.cgl.service.UserService;
import com.cgl.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cgl.service.CommentService;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-03-27 21:53:39
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;

    //写评论
    @Override
    public int comment(Comment comment) {
        System.out.println(comment);
        int insert = commentMapper.insert(comment);
        return insert;
    }

    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(Comment::getArticleId, articleId);
        //根评论 rootId为-1
        queryWrapper.eq(Comment::getRootId, -1);

        Page<Comment> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);

        List<CommentVo> commentVoList = new ArrayList<CommentVo>();
        //查询对应文章的根评论
//        List<Comment> commentsRoot = commentMapper.selectList(queryWrapper);
        List<Comment> commentsRoot = page.getRecords();
        int totalnum = (int) page.getTotal();
        System.out.println("所有的结果" + commentsRoot);
        List<Long> commentids = new ArrayList<Long>();
        //遍历根评论，记录根评论ID
        for (Comment comment : commentsRoot) {
            System.out.println("查询根评论" + comment.getId());
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comment, commentVo);
            commentVo.setUsername(userMapper.selectById(commentVo.getCreateBy()).getUsername());
            LambdaQueryWrapper<Comment> queryWrapperid = new LambdaQueryWrapper<>();
            queryWrapperid.eq(Comment::getRootId, comment.getId());
            //查询子评论
            List<Comment> childComments = commentMapper.selectList(queryWrapperid);
            //用于存放子评论
            List<CommentVo> childCommentVos = new ArrayList<CommentVo>();
            //遍历存放子评论
            for (Comment childcomment : childComments) {
                CommentVo childcommentVo = new CommentVo();
                BeanUtils.copyProperties(childcomment, childcommentVo);
                childcommentVo.setUsername(userMapper.selectById(childcommentVo.getCreateBy()).getUsername());
                childcommentVo.setToCommentUserName(userMapper.selectById(childcommentVo.getToCommentUserId()).getUsername());
                childCommentVos.add(childcommentVo);
            }
            commentVo.setChildren(childCommentVos);
            //将评论添加到list中（包含根评论与子评论）
            commentVoList.add(commentVo);
        }
        CommentPageVo commentPageVo = new CommentPageVo(totalnum,commentVoList);
        return ResponseResult.okResult(commentPageVo);
    }

    @Override
    public int getCommentNumByDate(String date, int userid) {
        return commentMapper.getNumByDate(date,userid);
    }

}

