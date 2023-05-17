package com.cgl.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cgl.model.vo.UserContributeVo;
import com.cgl.model.vo.UserDataInfoVo;
import com.cgl.service.ArticleService;
import com.cgl.service.CommentService;
import com.cgl.service.SqlGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/4/8
 */
@RestController
@RequestMapping("/user/datainfo")
public class DataInfoController {

    @Autowired
    SqlGenService sqlGenService;

    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;
    @GetMapping("/showUserUsedData")
    public List<UserDataInfoVo> showUserUsedData (HttpServletRequest request) {
        String UserId = sqlGenService.getIdByToken(request);
        int days = 7;
        Long t = System.currentTimeMillis();
        List<UserDataInfoVo> datalist = new ArrayList<>();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 0;i<days;i++){
            UserDataInfoVo info = new UserDataInfoVo();
            Date date = new Date(t - (1000*3600*24)*i);
            info.setDate(dateFormat.format(date));
            info.setArticleNum(articleService.getArticleNumByDate(dateFormat.format(date), Integer.parseInt(UserId)));
            info.setTabalDataNum(sqlGenService.getTableDataNumByDate(dateFormat.format(date), UserId));
            info.setCommentNum(commentService.getCommentNumByDate(dateFormat.format(date), Integer.parseInt(UserId)));
            datalist.add(info);
        }
        return datalist;
    }
    @GetMapping("/showUserContribute")
    public UserContributeVo showUserContribute (HttpServletRequest request) {
        String UserId = sqlGenService.getIdByToken(request);
        UserContributeVo contributeVo = new UserContributeVo();
        contributeVo.setDataType(sqlGenService.getDataTypeNumById(UserId));
        contributeVo.setColNum(sqlGenService.getColsNumById(UserId));
        return contributeVo;
    }
}
