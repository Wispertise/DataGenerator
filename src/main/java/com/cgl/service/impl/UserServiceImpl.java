package com.cgl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgl.enums.AppHttpCodeEnum;
import com.cgl.exception.SystemException;
import com.cgl.mapper.LoginLogMapper;
import com.cgl.mapper.UserMapper;
import com.cgl.model.ResponseResult;
import com.cgl.model.entity.Comment;
import com.cgl.model.entity.LoginUser;
import com.cgl.model.entity.User;
import com.cgl.model.po.LoginLog;
import com.cgl.model.vo.CommentVo;
import com.cgl.model.vo.UserInfoVo;
import com.cgl.model.vo.UserLoginVo;
import com.cgl.service.UserService;
import com.cgl.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-22 17:26:07
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Autowired
    UserMapper userMapper;
    @Autowired
    LoginLogMapper loginLogMapper;

    @Override
    public ResponseResult register(User user) {
        if (!StringUtils.hasText(user.getUsername())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        //TODO
        if (usernameExist(user.getUsername())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }

        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        System.out.println("----before----");
        save(user);
//        userMapper.insert(user);
        System.out.println("----after----");
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult login(User user/*, HttpServletResponse response*/, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或者密码错误");
        }

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        redisCache.setCacheObject("DgLogin:" + userId, loginUser);

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        UserLoginVo userLoginVo = new UserLoginVo(jwt, userInfoVo);
//        Cookie cookie = new Cookie("user-token",jwt);
//        response.addCookie(cookie);
        //记录登录日志

        Timestamp time = new Timestamp(System.currentTimeMillis());
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(Integer.parseInt(userId));
        loginLog.setLoginTime(time);
        loginLog.setIp(GetIp.getIpAddress(request));
        loginLogMapper.insert(loginLog);
        return ResponseResult.okResult(userLoginVo);
    }

    @Override
    public ResponseResult logout() {
        //获取token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取id
        Long userId = loginUser.getUser().getId();
        //删除redis中用户信息
        redisCache.deleteObject("DgLogin:" + userId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult userInfo() {
        //获取用户当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }
    @Override
    public ResponseResult updateUserPass(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        updateById(user);
        return ResponseResult.okResult();
    }
    @Override
    public ResponseResult checkPass(String userid,String password) {

        User user = userMapper.selectById(userid);
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        boolean matches = encode.matches(password, user.getPassword());
        if (matches) {
            return ResponseResult.okResult(200,"密码验证成功");
        } else {
            return ResponseResult.errorResult(100, "旧密码密码错误");
        }
    }

    private boolean usernameExist(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return count(queryWrapper) > 0;
    }


}

