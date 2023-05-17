package com.cgl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgl.model.dto.UserCols;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ColsMapper extends BaseMapper<UserCols> {

    @Update("update dg.cols set is_delete = 1 where id = #{colId} and user_id = #{UserId} and is_delete = 0")
    public int  fakeDelete(String UserId,int colId);

    @Update("update dg.cols set limits = 'private' where id = #{colId} and user_id = #{UserId} and is_delete = 0")
    public int  setPublicToPrivate(String UserId,int colId);

    @Update("update dg.cols set limits = 'public' where id=#{colId} and user_id = #{UserId} and is_delete = 0")
    public int  setPrivateToPublic(String UserId,int colId);
    @Select("select count(*) from dg.cols where user_id = #{userId} and limits = 'public' and is_delete = 0")
    public int getNumById(String userId);
}
