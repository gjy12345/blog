package cn.gjy.blog.dao.method;

import cn.gjy.blog.framework.Invocation.CusMethodSql;
import cn.gjy.blog.model.Category;
import cn.gjy.blog.utils.StringUtils;

import java.util.Arrays;

/**
 * @Author gujianyang
 * @Date 2020/12/11
 * @Class CategoryMethodSql
 */
public class CategoryMethodSql {

    public static class CategoryCountMethod implements CusMethodSql{

        @Override
        public SqlAndArgs handle(Object... data) {
            Category category= (Category) data[0];
            StringBuilder sql=new StringBuilder("select count(*) from category where 1=1 ");
            Object[] args=new Object[10];
            int index=0;
            if(!StringUtils.isEmptyString(category.getName())){
                sql.append("and name like concat('%',?,'%') ");
                args[index++]=category.getName();
            }
            if(category.getLock()!=null){
                sql.append("and lock=? ");
                args[index++]=category.getName();
            }
            return SqlAndArgs.build(sql.toString(), Arrays.copyOf(args,index));
        }
    }

    public static class CategoryDataMethod implements CusMethodSql{

        @Override
        public SqlAndArgs handle(Object... data) {
            Category category= (Category) data[0];
            if(category!=null&&data[1]==null&&data[2]==null){
                return SqlAndArgs.build("select * from category where create_user=? order by id desc",
                        category.getCreateUser());
            }
            Integer page= (Integer) data[1];
            Integer size= (Integer) data[2];
            StringBuilder sql=new StringBuilder("select * from category where 1=1 ");
            Object[] args=new Object[10];
            int index=0;
            if(!StringUtils.isEmptyString(category.getName())){
                sql.append("and name like concat('%',?,'%') ");
                args[index++]=category.getName();
            }
            if(category.getLock()!=null){
                sql.append("and lock=? ");
                args[index++]=category.getName();
            }
            sql.append(" order by id desc limit ").append(page*10).append(", ").append(size);
            return SqlAndArgs.build(sql.toString(), Arrays.copyOf(args,index));
        }
    }

}
