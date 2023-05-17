package com.cgl.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/3/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("table_data")
public class TableData {
    private Integer Id;
    private String TableName;
    private String TableSql;
    private String UserId;
    private String limits;
    private Timestamp AddTime;
    private String InsertSql;
    private int isDelete;
}
