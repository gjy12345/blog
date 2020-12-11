package cn.gjy.blog.dao;

import cn.gjy.blog.framework.annotation.BindParam;
import cn.gjy.blog.framework.annotation.Dao;
import cn.gjy.blog.framework.annotation.Insert;
import cn.gjy.blog.framework.annotation.Select;
import cn.gjy.blog.model.SysOperation;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/9
 * @Class CommonDao
 */
@Dao
public interface CommonDao {

    @Select("select * from sys_operation where user_id=#{user_id} " +
            "order by id desc limit #{limit}")
    List<SysOperation> getUserOperation(@BindParam("user_id") Integer userId,
                                        @BindParam("limit") Integer limit);

    @Insert("INSERT INTO `sys_operation`( `user_id`, `operation`, `operation_type`, `operation_time`, `ip`, `client`)" +
            " VALUES ( #{userId}, #{operation}, #{operation_type}, #{createTime}, #{ip}, #{client});")
    int insertLog(@BindParam("userId") Integer id, @BindParam("operation_type") int operationType,
                  @BindParam("operation") String operation,@BindParam("createTime") String createTime,
                  @BindParam("ip") String ip,@BindParam("client") String client);
}
