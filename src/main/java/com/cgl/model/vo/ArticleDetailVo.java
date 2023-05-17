package com.cgl.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //访问量
    private Long viewCount;
    //创建时间
    private Date createTime;
    //点赞数
    private int likes;
}