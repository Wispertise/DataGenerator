package com.cgl.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:sql结果视图对象
 * @author: SunnyDeer
 * @time: 2023/3/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SqlRes {
    private String create_sql;
    private String insert_sql;
    private String json_sql;

}
