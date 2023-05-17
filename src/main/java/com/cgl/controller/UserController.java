package com.cgl.controller;

import com.cgl.model.ResponseResult;
import com.cgl.model.dto.CheckPassDTO;
import com.cgl.model.entity.User;
import com.cgl.service.LoginLogService;
import com.cgl.service.SqlGenService;
import com.cgl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    SqlGenService sqlGenService;
    @Autowired
    LoginLogService loginLogService;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user, HttpServletRequest request) {
        return userService.login(user,request);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return userService.logout();
    }

    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/updateUserInfo")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }
    @PutMapping("/updateUserPass")
    public ResponseResult updateUserPass(@RequestBody User user){
        return userService.updateUserPass(user);
    }
    @GetMapping("/getLoginLog")
    public ResponseResult getLoginLog(HttpServletRequest request, Integer pageNum, Integer pageSize){
        String userid = sqlGenService.getIdByToken(request);
        System.out.println("pageNum "+pageNum+" pageSize "+pageSize);
        ResponseResult logInfo = loginLogService.getLogInfo(Integer.valueOf(userid), pageNum, pageSize);
        return logInfo;
    }
    @PostMapping("/checkOldPass")
    public ResponseResult checkOldPass(HttpServletRequest request,@RequestBody CheckPassDTO checkPassDTO){

        String userid = sqlGenService.getIdByToken(request);
        return userService.checkPass(userid,checkPassDTO.getOldpassword());
    }
}
