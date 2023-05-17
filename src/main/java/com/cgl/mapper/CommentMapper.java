package com.cgl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgl.model.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-27 21:53:37
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    @Insert("INSERT INTO dg.comment (article_id, root_id, content, to_comment_user_id, to_comment_id, create_by, create_time, update_by ,  update_time ,  del_flag ) VALUES (#{articleId}, #{rootId}, #{content},  #{toCommentUserId},  #{toCommentId},  #{createBy},  #{createTime},  #{updateBy},  #{updateTime}, '0')")
    public int insertComment(Comment comment);
    @Select("SELECT count(*) FROM dg.comment where substr(concat(create_time),1,10) = #{dateStr} and create_by = #{userid} and del_flag = 0 order by create_time")
    public int getNumByDate(String dateStr,int userid);
}

