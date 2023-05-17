package com.cgl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cgl.mapper.*;
import com.cgl.model.entity.Comment;
import com.cgl.model.po.TableData;
import com.cgl.model.vo.UserDataInfoVo;
import com.cgl.service.SqlGenService;
import com.cgl.service.impl.SqlGenServiceImpl;
import com.cgl.utils.RandomDataCreateUtil;
import com.cgl.utils.TxtWriterUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class MyDgApplicationTests {

    @Autowired
    RandomDataCreateUtil randomDataCreateUtil;
    @Autowired
    SqlDataMapper sqlDataMapper;
    @Autowired
    DataTypeMapper dataTypeMapper;
    @Autowired
    TableDataMapper tableDataMapper;

    @Autowired
    SqlGenService sqlGenService;

    @Autowired
    CommentMapper commentMapper;
    @Test
    void contextLoads() {
//
//        Comment comment = new Comment();
//        comment.setArticleId(Long.valueOf("12"));
//        comment.setRootId(Long.valueOf("-1"));
//        comment.setContent("helloWorld");
//        comment.setToCommentUserId(Long.valueOf("-1"));
//        comment.setToCommentId(Long.valueOf("-1"));
//        Date date = new Date(System.currentTimeMillis());//获得当前时间
//        Timestamp t = new Timestamp(date.getTime());//将时间转换成Timestamp类型，这样便可以存入到Mysql数据库中
//        comment.setCreateTime(t);
//
//        System.out.println(commentMapper.insert(comment));
//        System.out.println(dataTypeMapper.getDataType("2"));

//        int UserId = 6;
//        int days = 7;
//        Long t = System.currentTimeMillis();
//        List<String> datelist = new ArrayList<>();
//        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
//
//        for(int i = 0;i<days;i++){
//            Date date = new Date(t - (1000*3600*24)*i);
//            datelist.add(dateFormat.format(date));
//        }
//        System.out.println(commentMapper.getNumByDate("2023-03-29",6));

        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        ArrayList<String> row1 = new ArrayList<String>();
        row1.add("奥v你");
        row1.add("奥v你");
        row1.add("奥v你");
        row1.add("奥v你");
        list.add(row1);
        ArrayList<String> row2 = new ArrayList<String>();
//        row2.add();
        row2.add("4");
        row2.add("4");
        row2.add("4");
        list.add(row2);
        // 指定保存文件的路径和文件名
        String filePath = "C:/Users/23346/Documents/ideaworkspace/dg/txt/";
//        TxtWriterUtil.writeIntoTxt(filePath,list);
        System.out.println(list);
    }
}

