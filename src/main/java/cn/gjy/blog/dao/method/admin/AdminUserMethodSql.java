package cn.gjy.blog.dao.method.admin;

import cn.gjy.blog.framework.Invocation.CusMethodSql;
import cn.gjy.blog.model.SysUser;
import cn.gjy.blog.utils.StringUtils;

import java.util.Arrays;

/**
 * @Author gujianyang
 * @Date 2020/12/20
 * @Class UserMethodSql
 */
public class AdminUserMethodSql {

    public static class UserMethodData implements CusMethodSql {

        @Override
        public SqlAndArgs handle(Object... data) {
            StringBuilder sb=new StringBuilder("select sys_user.* from sys_user where user_type=");
            sb.append(SysUser.USER);
            sb.append(" ");
            SysUser sysUser= (SysUser) data[0];
            String keyword= (String) data[1];
            Integer page= (Integer) data[2];
            Integer size= (Integer) data[3];
            int index=0;
            Object[] objects=new Object[10];
            if(!StringUtils.isEmptyString(keyword)){
                sb.append(" and (username like concat('%',?,'%') or nickname like concat('%',?,'%') )");
                objects[index++]=keyword;
                objects[index++]=keyword;
            }else {
                if(!StringUtils.isEmptyString(sysUser.getUsername())){
                    sb.append(" and username like concat('%',?,'%') ");
                    objects[index++]=sysUser.getNickname();
                }
                if(!StringUtils.isEmptyString(sysUser.getNickname())){
                    sb.append(" and nickname like concat('%',?,'%') ");
                    objects[index++]=sysUser.getNickname();
                }
            }
            if(sysUser.getLock()!=null){
                sb.append(" and sys_user.lock=? ");
                objects[index++]=sysUser.getLock();
            }
            if(sysUser.getSex()!=null){
                sb.append(" and sys_user.sex=? ");
                objects[index++]=sysUser.getSex();
            }
            sb.append("order by sys_user.id desc limit ").append(page*10).append(", ").append(size);
            return SqlAndArgs.build(sb.toString(), Arrays.copyOf(objects,index));
        }
    }

    //只显示普通用户
    public static class UserMethodCount implements CusMethodSql {

        @Override
        public SqlAndArgs handle(Object... data) {
            if(data[0]==null){
                return SqlAndArgs.build("select count(*) from sys_user where user_type=?", SysUser.USER);
            }
            StringBuilder sb=new StringBuilder("select count(*) from sys_user where user_type=");
            sb.append(SysUser.USER);
            sb.append(" ");
            SysUser sysUser= (SysUser) data[0];
            String keyword= (String) data[1];
            int index=0;
            Object[] objects=new Object[10];
            if(!StringUtils.isEmptyString(keyword)){
                sb.append(" and (username like concat('%',?,'%') or nickname like concat('%',?,'%') )");
                objects[index++]=keyword;
                objects[index++]=keyword;
            }else {
                if(!StringUtils.isEmptyString(sysUser.getUsername())){
                    sb.append(" and username like concat('%',?,'%') ");
                    objects[index++]=sysUser.getNickname();
                }
                if(!StringUtils.isEmptyString(sysUser.getNickname())){
                    sb.append(" and nickname like concat('%',?,'%') ");
                    objects[index++]=sysUser.getNickname();
                }
            }
            if(sysUser.getLock()!=null){
                sb.append(" and sys_user.lock=? ");
                objects[index++]=sysUser.getLock();
            }
            if(sysUser.getSex()!=null){
                sb.append(" and sys_user.sex=? ");
                objects[index++]=sysUser.getSex();
            }
            return SqlAndArgs.build(sb.toString(), Arrays.copyOf(objects,index));
        }
    }
}
