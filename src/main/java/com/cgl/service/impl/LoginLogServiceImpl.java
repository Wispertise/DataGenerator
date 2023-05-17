package com.cgl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgl.mapper.CommentMapper;
import com.cgl.mapper.LoginLogMapper;
import com.cgl.model.ResponseResult;
import com.cgl.model.entity.Comment;
import com.cgl.model.po.LoginLog;
import com.cgl.model.vo.CommentVo;
import com.cgl.model.vo.LoginLogVo;
import com.cgl.service.LoginLogService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/4/14
 */
@Service("loginLogService")
public class LoginLogServiceImpl  extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {
    @Override
    public ResponseResult getLogInfo(int userid, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<LoginLog> queryWrapper = new LambdaQueryWrapper<>();
        System.out.println("userid"+userid);
        queryWrapper.eq(LoginLog::getUserId,userid);
        Page<LoginLog> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);
        List<LoginLog> loginLogs = page.getRecords();
        LoginLogVo loginLogVo = new LoginLogVo();
        loginLogVo.setList(loginLogs);
        loginLogVo.setTotal((int) page.getTotal());
        return ResponseResult.okResult(loginLogVo);
    }
}
