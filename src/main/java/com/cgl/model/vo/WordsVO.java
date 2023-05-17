package com.cgl.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:显示词库
 * @author: SunnyDeer
 * @time: 2023/3/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordsVO {
    private String table_name;
    private String value;
    private String limits;
    private String user_id;
}
