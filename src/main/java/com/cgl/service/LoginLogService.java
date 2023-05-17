package com.cgl.service;

import com.cgl.model.ResponseResult;

public interface LoginLogService {
    //获取登录日志
    ResponseResult getLogInfo(int userid, Integer pageNum, Integer pageSize);

//    ResponseResult deleteLog()
}
