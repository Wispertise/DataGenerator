package com.cgl.controller;

import com.cgl.model.dto.UserCols;
import com.cgl.service.SqlGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/3/20
 */
@RestController
@RequestMapping("/col")
public class ColController {
    @Autowired
    SqlGenService sqlGenService;

    @PostMapping("/addCols")
    public int AddCols(@RequestBody UserCols userCols) {
        System.out.println(userCols);
        int i = sqlGenService.AddNewCol(userCols);
        return i;
    }

    @GetMapping()
    public List<UserCols> GetAllPublicCols() {

        List<UserCols> allPublicCols = sqlGenService.getAllPublicCols();
        return allPublicCols;
    }

    @GetMapping("/PersonalCol")
    public List<UserCols> getPersonalCols(HttpServletRequest request) {
        String userid = sqlGenService.getIdByToken(request);
        List<UserCols> personalCols = sqlGenService.getPersonalCols(userid);
        return personalCols;
    }

    @DeleteMapping("/{colId}")
    public int DeleteCol(@PathVariable int colId,HttpServletRequest request) {
        String userid = sqlGenService.getIdByToken(request);
        int i = sqlGenService.deleteCols(userid, colId);
        return i;
    }

    @GetMapping("/private/{colId}")
    public int PrivateCol(@PathVariable int colId,HttpServletRequest request) {
        System.out.println("colId="+colId);
        String userId = sqlGenService.getIdByToken(request);
        int i = sqlGenService.privateCols(userId, colId);
        return i;
    }

    @GetMapping("/public/{colId}")
    public int PublishCol(@PathVariable int colId,HttpServletRequest request) {
        String userId = sqlGenService.getIdByToken(request);
        int i = sqlGenService.publishCols(userId, colId);
        return i;
    }
}
