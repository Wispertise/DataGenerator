package com.cgl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgl.model.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 社区文章表(Article)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-26 08:35:34
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("SELECT count(*) FROM dg.article where substr(concat(create_time),1,10) = #{datestr} and create_by = #{userid} and del_flag = 0 order by create_time")
    public int getNumByDate(String datestr,int userid);
    @Update("update dg.article set view_count = view_count + 1 where id = #{id}")
    public int addViewCount(Long id);
    @Update("update dg.article set likes = likes + 1 where id = #{id} ")
    public int addlikes(int id);

}

