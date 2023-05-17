package com.cgl.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgl.model.po.TableData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TableDataMapper extends BaseMapper<TableData> {

//    @Insert("insert into table_data values(null,#{table_name},#{table_sql},#{user_id},#{limits},#{add_time},#{insert_sql})")
//    public int insert(TableData tableData);

    @Update("update table_data set is_delete = 1 where user_id=#{UserId} and table_name = #{TableName}")
    public int  fakeDeleteData(String UserId,String TableName);
    @Update("update table_data set limits = 'public' where user_id=#{UserId} and table_name = #{TableName}")
    public int  setPrivateToPublic(String UserId,String TableName);
    @Update("update table_data set limits = 'private' where user_id=#{UserId} and table_name = #{TableName}")
    public int  setPublicToPrivate(String UserId,String TableName);
    @Select("SELECT count(*) FROM dg.table_data where substr(concat(add_time),1,10) = #{datestr} and user_id = #{userid} and is_delete = 0 order by add_time")
    public int getNumByDate(String datestr,String userid);
}
