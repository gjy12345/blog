package cn.gjy.blog.dao.method.admin;

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
public class AdminCommentMethodSql {

    public static class CommentCountMethod implements CusMethodSql {

        @Override
        public SqlAndArgs handle(Object... data) {
            Integer userId= (Integer) data[0];
            String keyword= (String) data[1];
            StringBuilder sb=new StringBuilder("select count(*) from `comment`\n" +
                    "where 1=1 ");
            Object[] args=new Object[10];
            int index=0;
            if(!StringUtils.isEmptyString(keyword)){
                sb.append("and content like concat('%',?,'%') ");
                args[index++]=keyword;
            }
            if(userId!=null){
                sb.append("and user_id=? ");
                args[index++]=userId;
            }
            sb.append("order by id desc ");
            return SqlAndArgs.build(sb.toString(), Arrays.copyOf(args,index));
        }
    }

    public static class CommentDataMethod implements CusMethodSql {

        @Override
        public SqlAndArgs handle(Object... data) {
            Integer userId= (Integer) data[0];
            String keyword= (String) data[1];
            Integer page= (Integer) data[2];
            Integer size= (Integer) data[3];
            StringBuilder sb=new StringBuilder("select comment.*,article.title as articleTitle" +
                    ",sys_user.nickname as userName,article.url as articleUrl from `comment` " +
                    "left join article on article.id=comment.article_id " +
                    "left join sys_user on sys_user.id=comment.user_id  " +
                    "where 1=1 ");
            Object[] args=new Object[10];
            int index=0;
            if(!StringUtils.isEmptyString(keyword)){
                sb.append("and comment.content like concat('%',?,'%') ");
                args[index++]=keyword;
            }
            if(userId!=null){
                sb.append("and comment.user_id=? ");
                args[index++]=userId;
            }
            sb.append("order by comment.id desc limit ").append(page*10).append(", ").append(size);
            return SqlAndArgs.build(sb.toString(), Arrays.copyOf(args,index));
        }
    }
}
