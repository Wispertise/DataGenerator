package com.cgl.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgl.model.po.DataType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DataTypeMapper extends BaseMapper<DataType> {

    @Update("update dg.data_type set is_delete = 1 where type_name=#{DataType} and user_id = #{UserId} and is_delete = 0")
    public int  fakeDelete(String UserId,String DataType);

    @Update("update dg.data_type set limits = 'private' where type_name=#{DataType} and user_id = #{UserId} and is_delete = 0")
    public int  setPublicToPrivate(String UserId,String DataType);

    @Update("update dg.data_type set limits = 'public' where type_name=#{DataType} and user_id = #{UserId} and is_delete = 0")
    public int  setPrivateToPublic(String UserId,String DataType);

    @Select("select * from dg.data_type where (limits = 'public') or (user_id = #{userId}) and is_delete = 0")
    public List<DataType> getDataType(String userId);
    @Select("select count(*) from dg.data_type where user_id = #{userId} and limits = 'public' and is_delete = 0")
    public int getNumById(String userId);
}
