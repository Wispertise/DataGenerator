package com.cgl.model.vo;

import com.cgl.model.po.LoginLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/4/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginLogVo {
    private int total;
    private List<LoginLog> list;
}
