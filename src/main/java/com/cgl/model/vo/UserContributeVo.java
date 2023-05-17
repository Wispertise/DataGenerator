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
public class UserContributeVo {
    private int colNum;//贡献的字段数目（公共）
    private int dataType;//贡献的词库个数（公共）
}
