package com.cgl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgl.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-22 17:26:01
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {



}

