package cn.gjy.blog.dao;

import cn.gjy.blog.framework.annotation.BindParam;
import cn.gjy.blog.framework.annotation.Dao;
import cn.gjy.blog.framework.annotation.Insert;
import cn.gjy.blog.framework.annotation.Select;
import cn.gjy.blog.model.Comment;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/15
 * @Class CommentDao
 */
@Dao
public interface CommentDao {
    
    @Select("select count(*) from comment where article_id=#{id}")
    int selectArticleCommentsCount(@BindParam("id") Integer id);

    @Select("select comment.*,sys_user.nickname as userName from comment " +
            "left join sys_user " +
            "on sys_user.id=comment.user_id " +
            "where comment.article_id=#{id} order by id limit #{s},#{e}")
    List<Comment> selectArticleComments(@BindParam("id") Integer id,@BindParam("s") int start
            ,@BindParam("e") int end);

    @Insert("INSERT INTO `comment` \n" +
            "( `article_id`, `user_id`, `content`, `common_time`, `create_time_l`, `user_type` )\n" +
            "VALUES\n" +
            "\t( #{article_id}, #{user_id}, #{content}, #{common_time}, #{create_time_l}, " +
            "#{user_type} );")
    int addNewComment(Comment comment);
}
