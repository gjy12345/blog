package cn.gjy.blog.dao.method.admin;

import cn.gjy.blog.framework.Invocation.CusMethodSql;
import cn.gjy.blog.model.Article;
import cn.gjy.blog.utils.StringUtils;

import java.util.Arrays;

/**
 * @Author gujianyang
 * @Date 2020/12/20
 * @Class BlogMehodSql
 */
public class AdminBlogMethodSql {

    public static class BlogCountMethod implements CusMethodSql {

        @Override
        public SqlAndArgs handle(Object... data) {
            Article article= (Article) data[0];
            Object[] args=new Object[10];
            int index=0;
            StringBuilder sb=new StringBuilder("select count(*) from article where 1=1 ");
            if(article.getPublicityLevel()!=null){
                sb.append("and publicity_level=? ");
                args[index++]=article.getPublicityLevel();
            }
            if(article.getType()!=null){
                sb.append("and type=? ");
                args[index++]=article.getType();
            }
            if(!StringUtils.isEmptyString(article.getTitle())){
                sb.append("and title like concat('%',?,'%') ");
                args[index++]=article.getTitle();
            }
            return SqlAndArgs.build(sb.toString(), Arrays.copyOf(args,index));
        }
    }

    public static class BlogDataMethod implements CusMethodSql{

        @Override
        public SqlAndArgs handle(Object... data) {
            Article article= (Article) data[0];
            Object[] args=new Object[10];
            int index=0;
            StringBuilder sb=new StringBuilder("select article.id,\n" +
                    "article.create_time,article.update_time,\n" +
                    "article.comment,article.description,\n" +
                    "article.keywords,\n" +
                    "article.password,\n" +
                    "article.status,\n" +
                    "article.title,\n" +
                    "article.type,\n" +
                    "article.url,\n" +
                    "article.visit,\n" +
                    "article.publicity_level\n" +
                    ",category.name as typeName from article left join category\n" +
                    "on category.id=article.type where 1=1 ");
            Integer page= (Integer) data[1];
            Integer size= (Integer) data[2];
            if(article.getPublicityLevel()!=null){
                sb.append("and article.publicity_level=? ");
                args[index++]=article.getPublicityLevel();
            }
            if(article.getType()!=null){
                sb.append("and article.type=? ");
                args[index++]=article.getType();
            }
            if(!StringUtils.isEmptyString(article.getTitle())){
                sb.append("and article.title like concat('%',?,'%') ");
                args[index++]=article.getTitle();
            }
            sb.append(" order by id desc limit ").append(page*10).append(", ").append(size);
            return SqlAndArgs.build(sb.toString(), Arrays.copyOf(args,index));
        }
    }
}
