package com.cgl.model.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("cols")
public class UserCols {
    private String id; //属性id
    private String colName; //属性名称
    private String colType;//属性值的数据类型
    private String colComment;//属性注释
    private String isNotNull;//属性值非空，true表示为非空
    private String isKey;//属性是否为主键，true表示为主键
    private String autoIncrease;//是否自增，为true表示为自增
    private String defaultVal;//默认值
    private String valType;//数据的种类
    private String valRule;//数据规则{随机、自增}
    private String valStart;//起始数据
    private String userId;//所属有用户id
    private String limits;//是否公开
    private String isDelete; //是否已经删除
}
