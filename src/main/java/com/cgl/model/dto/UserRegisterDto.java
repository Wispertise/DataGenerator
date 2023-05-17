package com.cgl.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 */
@Data
public class UserRegisterDto implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String name;

    private String account;

    private String password;

    private String checkPassword;
}
