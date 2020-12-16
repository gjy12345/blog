package cn.gjy.blog.dao.method;

import cn.gjy.blog.framework.Invocation.CusMethodSql;
import cn.gjy.blog.model.SysUser;
import cn.gjy.blog.utils.StringUtils;

import java.util.Arrays;

/**
 * @Author gujianyang
 * @Date 2020/12/16
 * @Class CommentMethodSql
 */
@SuppressWarnings("all")
public class CommentMethodSql {

    public static class CommentCountMethod implements CusMethodSql {

        @Override
        public SqlAndArgs handle(Object... data) {
            SysUser user= (SysUser) data[0];
            String keyword= (String) data[1];
            Integer showType= (Integer) data[2];
            StringBuilder sb=new StringBuilder("select count(*) from `comment`\n" +
                    "where `comment`.article_id in (select id from article where article.user_id=?) ");
            Object[] args=new Object[10];
            int index=0;
            args[index++]=user.getId();
            if(!StringUtils.isEmptyString(keyword)){
                sb.append("and content like concat('%',?,'%') ");
                args[index++]=keyword;
            }
            if(showType!=null){
                if(showType==0){
                    sb.append("and comment.user_id=? ");
                    args[index++]=user.getId();
                }else if(showType==1){
                    sb.append("and comment.user_id<>? ");
                    args[index++]=user.getId();
                }
            }
            sb.append("order by id desc ");
            return SqlAndArgs.build(sb.toString(), Arrays.copyOf(args,index));
        }
    }

    public static class CommentDataMethod implements CusMethodSql {

        @Override
        public SqlAndArgs handle(Object... data) {
            SysUser user= (SysUser) data[0];
            String keyword= (String) data[1];
            Integer page= (Integer) data[2];
            Integer size= (Integer) data[3];
            Integer showType= (Integer) data[4];
            StringBuilder sb=new StringBuilder("select comment.*,article.title as articleTitle" +
                    ",sys_user.nickname as userName,article.url as articleUrl from `comment` " +
                    "left join article on article.id=comment.article_id " +
                    "left join sys_user on sys_user.id=comment.user_id  " +
                    "where `comment`.article_id in (select id from article where article.user_id=?) ");
            Object[] args=new Object[10];
            int index=0;
            args[index++]=user.getId();
            if(!StringUtils.isEmptyString(keyword)){
                sb.append("and comment.content like concat('%',?,'%') ");
                args[index++]=keyword;
            }
            if(showType!=null){
                if(showType==0){
                    sb.append("and comment.user_id=? ");
                    args[index++]=user.getId();
                }else if(showType==1){
                    sb.append("and comment.user_id<>? ");
                    args[index++]=user.getId();
                }
            }
            sb.append("order by comment.id desc limit ").append(page*10).append(", ").append(size);
            return SqlAndArgs.build(sb.toString(), Arrays.copyOf(args,index));
        }
    }
}
