package com.cgl.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/4/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginLog {
    private int id;
    private int userId;
    private Timestamp loginTime;
    private String ip;
//    private int isdelete;
}
