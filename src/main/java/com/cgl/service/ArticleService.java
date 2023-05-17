package com.cgl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cgl.model.ResponseResult;
import com.cgl.model.dto.AddArticleDto;
import com.cgl.model.dto.EditArticleDto;
import com.cgl.model.entity.Article;


/**
 * 社区文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2023-03-26 08:35:38
 */
public interface ArticleService extends IService<Article> {

    ResponseResult articleList(Integer pageNum, Integer pageSize);

    ResponseResult addArticle(AddArticleDto addArticleDto);

    ResponseResult deleteArticle(Long id);

    ResponseResult getInfoById(Long id);

    ResponseResult editArticleById(EditArticleDto editArticleDto);

    ResponseResult getArticleDetail(Long id);

    ResponseResult selfArticleList(Integer pageNum, Integer pageSize);

    public int getArticleNumByDate(String date,int userid);
    public ResponseResult addLikes(int id);

}

