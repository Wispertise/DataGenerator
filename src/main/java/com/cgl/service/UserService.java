package com.cgl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cgl.model.ResponseResult;
import com.cgl.model.entity.User;
import com.cgl.model.po.LoginLog;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-03-22 17:26:07
 */
public interface UserService extends IService<User> {
    ResponseResult register(User user);

    ResponseResult login(User user, HttpServletRequest request);

    ResponseResult logout();

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);
    ResponseResult updateUserPass(User user) ;
    ResponseResult checkPass(String userid,String password);
}

