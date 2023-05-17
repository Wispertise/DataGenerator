package com.cgl.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @description:sql数据接受对象
 * @author: SunnyDeer
 * @time: 2023/3/8
 */
import java.util.ArrayList;
/**
 * @description:接受提交的生成sql数据
 * @author: SunnyDeer
 * @time: 2023/3/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SqlTable {
    private String table_name; //表名
    private String table_comment;//表注释
    private Integer row_num;//生成的数据行数
    private ArrayList<Col> cols;//属性
    private String limits;      // 权限
}