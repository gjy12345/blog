package cn.gjy.blog.dao;

import cn.gjy.blog.framework.annotation.*;
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

    @Select("select * from comment where id=#{id}")
    Comment selectCommentById(@BindParam("id") Integer id);

    @Delete("delete from comment where id=#{id}")
    int deleteComment(@BindParam("id") Integer id);

    @Select("SELECT count(*) from `comment` \n" +
            "where article_id in (SELECT id from article where user_id=#{userId})")
    int getUserAllCommentCount(@BindParam("userId") Integer id);

    @Select("SELECT `comment`.* , sys_user.nickname as userName,article.title as articleTitle,\n" +
            "article.url as articleUrl \n" +
            "from `comment` \n" +
            "left join sys_user\n" +
            "on sys_user.id=`comment`.user_id\n" +
            "left join article\n" +
            "on article.id=`comment`.article_id\n" +
            "where article_id in (SELECT id from article where user_id=#{userId})\n" +
            "order by `comment`.id desc limit 0,5;\n")
    List<Comment> selectUserRecentComments(@BindParam("userId")Integer id);

    @Delete("delete from comment where user_id=#{id}")
    int deleteCommentByUserId(@BindParam("id") Integer id);
}
