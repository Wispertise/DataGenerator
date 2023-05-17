package com.cgl.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/4/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageVo {
    private int total;
    private List<CommentVo> list;
}
