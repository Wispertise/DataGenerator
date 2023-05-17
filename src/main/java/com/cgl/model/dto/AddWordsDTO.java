package com.cgl.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:添加词库
 * @author: SunnyDeer
 * @time: 2023/3/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddWordsDTO {
    private String table_name;
    private String value;
    private String limits;
}
