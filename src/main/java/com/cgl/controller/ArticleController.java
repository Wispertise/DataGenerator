package com.cgl.controller;

import com.cgl.model.ResponseResult;
import com.cgl.model.dto.AddArticleDto;
import com.cgl.model.dto.EditArticleDto;
import com.cgl.model.vo.PageVo;
import com.cgl.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/community/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize){
        return articleService.articleList(pageNum,pageSize);
    }

    @GetMapping("/selfArticleList")
    public ResponseResult selfArticleList(Integer pageNum, Integer pageSize){
        return articleService.selfArticleList(pageNum,pageSize);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) throws Exception {
        return articleService.getArticleDetail(id);
    }
//    @PutMapping("/updateViewCount/{id}")
//    public ResponseResult updateViewCount(@PathVariable("id") Long id){
//        return articleService.updateViewCount(id);
//    }

    @PostMapping("/addArticle")
    public ResponseResult addArticle(AddArticleDto addArticleDto){
        return articleService.addArticle(addArticleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable("id") Long id){
        return articleService.deleteArticle(id);
    }

    @GetMapping("/edit/{id}")
    public ResponseResult getInfoById(@PathVariable("id") Long id){
        return articleService.getInfoById(id);
    }
    @PutMapping("/edit")
    public ResponseResult editArticleById(@RequestBody EditArticleDto editArticleDto){
        return articleService.editArticleById(editArticleDto);
    }
    @GetMapping("/likes/{id}")
    public ResponseResult addLikes(@PathVariable("id") int id){
        System.out.println("111");
        return articleService.addLikes(id);
    }



}
