package com.cgl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgl.mapper.ArticleMapper;
import com.cgl.model.ResponseResult;
import com.cgl.model.dto.AddArticleDto;
import com.cgl.model.dto.EditArticleDto;
import com.cgl.model.entity.Article;
import com.cgl.model.entity.LoginUser;
import com.cgl.model.entity.User;
import com.cgl.model.vo.ArticleDetailVo;
import com.cgl.model.vo.ArticleListVo;
import com.cgl.model.vo.PageVo;
import com.cgl.utils.BeanCopyUtils;
import com.cgl.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cgl.service.ArticleService;

import java.util.List;
import java.util.stream.Collectors;

import static com.cgl.constant.SystemConstants.ARTICLE_STATUS_NORMAL;
import static com.cgl.utils.JwtUtil.parseJWT;

/**
 * 社区文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-03-26 08:35:38
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //状态需为正式发布状态
        queryWrapper.eq(Article::getStatus,ARTICLE_STATUS_NORMAL);
        //对浏览次数进行排序
        queryWrapper.orderByDesc(Article::getViewCount);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addArticle(AddArticleDto addArticleDto) {
        Article article = BeanCopyUtils.copyBean(addArticleDto,Article.class);
        save(article);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        Article article = getById(id);
        return ResponseResult.okResult(article);
    }

    @Override
    public ResponseResult editArticleById(EditArticleDto editArticleDto) {
        Article article = BeanCopyUtils.copyBean(editArticleDto, Article.class);
        updateById(article);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //增加浏览量
        articleMapper.addViewCount(id);
        //根据id查询文章
        Article article = getById(id);
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult selfArticleList(Integer pageNum, Integer pageSize) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //状态需为正式发布状态
        queryWrapper.eq(Article::getStatus,ARTICLE_STATUS_NORMAL);
        //对浏览次数进行排序
        queryWrapper.orderByDesc(Article::getViewCount);

        //获取当前登录用户的账号
        Long userId = SecurityUtils.getUserId();

        queryWrapper.eq(Article::getCreateBy,userId);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * @param date:  日期
     * @param userid:
     * @return int
     * @author SunnyDeer
     * @description 查询指定日期内的文章个数
     * @date 2023/4/8 21:24
     */
    public int getArticleNumByDate(String date,int userid){
        int numByDate = articleMapper.getNumByDate(date, userid);
        return numByDate;
    }

    @Override
    public ResponseResult addLikes(int id) {
        String msg ;
        int addlikes = articleMapper.addlikes(id);
        if(addlikes>0){
            msg = "点赞成功";
        }else{
            msg = "点赞失败";
        }
        return ResponseResult.okResult(msg);
    }
}

