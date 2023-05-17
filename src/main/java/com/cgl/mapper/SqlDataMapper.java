package com.cgl.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgl.model.po.SqlData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SqlDataMapper extends BaseMapper<SqlData> {


    @Insert("insert into dg.sql_data values(null,#{Value},#{DataType},#{UserId},#{DataLimits},0)")
    public int insert(SqlData sqlData);
    @Select("Select value From dg.sql_data where data_type=#{DataType} and is_delete = 0 Order By Rand() Limit #{num}")
    public List<String> RandomGet(String DataType,int num);
    @Update("update dg.sql_data set data_limits = 'public' where user_id=#{UserId} and data_type = #{DataType} and is_delete = 0")
    public int  setPrivateToPublic(String UserId,String DataType);
    @Update("update dg.sql_data set data_limits = 'private' where user_id=#{UserId} and data_type = #{DataType}  and is_delete = 0")
    public int  setPublicToPrivate(String UserId,String DataType);
    @Update("update dg.sql_data set is_delete = 1 where user_id=#{UserId} and data_type = #{DataType}")
    public int  fakeDelete(String UserId,String DataType);
}
