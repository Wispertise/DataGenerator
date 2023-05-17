package com.cgl.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserInfoVo {

    private Long id;

    private String username;

    private String avatar;

    private String gender;

    private String email;

    private String phoneNumber;

    private Date createTime;

    private Date updateTime;
}