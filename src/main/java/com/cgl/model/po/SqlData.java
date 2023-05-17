package com.cgl.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/3/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@TableName("sql_data")
public class SqlData {
    private int Id;
    private String Value;
    private String DataType;
    private String UserId;
    private String DataLimits;
    private int isDelete;
}
