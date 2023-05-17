package com.cgl.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @description: 属性列，
 * @author: SunnyDeer
 * @time: 2023/3/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Col {

    private String col_name; //属性名称
    private String col_type;//属性值的数据类型
    private String col_comment;//属性注释
    private String is_notNull;//属性值非空，true表示为非空
    private String is_key;//属性是否为主键，true表示为主键
    private String auto_increase;//是否自增，为true表示为自增
    private String default_val;//默认值
    private String val_type;//数据的种类
    private String val_rule;//数据规则{随机、自增}
    private String val_start;//起始数据

}
