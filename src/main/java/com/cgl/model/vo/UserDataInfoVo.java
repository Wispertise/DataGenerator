package com.cgl.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/4/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataInfoVo {
    private String date;//日期
    private int articleNum; //文章数目
    private int commentNum;//评论数目
    private int tabalDataNum;//生成的sql语句数目
}
