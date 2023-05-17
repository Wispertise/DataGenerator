package com.cgl.model.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/3/18
 */
@Data
public class TableVo {
    private Integer Id;
    private String TableName;
    private String TableSql;
    private String UserId;
    private String limits;
    private Timestamp AddTime;
    private String InsertSql;
    private String TableComment;

}
